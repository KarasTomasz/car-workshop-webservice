package pl.tkaras.carworkshopwebservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.EmailSendingException;
import pl.tkaras.carworkshopwebservice.exceptions.TokenNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repositories.RegistrationConfirmTokenRepository;
import pl.tkaras.carworkshopwebservice.securities.jwt.JwtUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    private final RegistrationConfirmTokenService registrationConfirmTokenService;

    private final RegistrationConfirmTokenRepository confirmTokenRepo;

    private final AppUserRepository appUserRepo;

    public List<AppUser> getAllUsersDetails(){
        return appUserRepo.findAll();
    }

    public AppUser getAppUser(String username){
        return appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));
    }

    public String login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        return (jwtUtils.getTokenPrefix() + jwtToken);
    }

    @Transactional
    public RegistrationConfirmToken register(AppUser appUser) {

        String token = signUp(appUser);

        if(token != null){
            try {
                String link = "http://localhost:8081/api/v1/auth/confirm?token=" + token;
                emailSenderService.send(appUser.getEmail(), buildEmailTemplate(appUser.getUsername(), link), true);
            }
            catch (Exception e){
                throw new EmailSendingException(appUser.getEmail());
            }
            return confirmTokenRepo.findByToken(token)
                    .orElseThrow(() -> new TokenNotFoundException(token));
        }
        return null;
    }

    public String signUp(AppUser appUser){

        if(!appUserRepo.existsByUsername(appUser.getUsername())){

            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

            String token = UUID.randomUUID().toString(); //generate register confirmation token

            appUserRepo.save(appUser);

            RegistrationConfirmToken confirmToken = RegistrationConfirmToken.builder()
                    .token(token)
                    .createdOn(LocalDateTime.now())
                    .expiresOn(LocalDateTime.now().plusMinutes(20)) //the token will expire after 20 minutes
                    .appUser(appUser)
                    .build();

            registrationConfirmTokenService.saveConfirmationToken(confirmToken);

            return token;
        }
        return null;
    }

    @Transactional
    public void confirm(String token){
        RegistrationConfirmToken registrationConfirmToken = confirmTokenRepo.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(token));

        registrationConfirmToken.setConfirmedOn(LocalDateTime.now());

        enableAppUser(registrationConfirmToken.getAppUser().getUsername());

        AppUser user = registrationConfirmToken.getAppUser();
        registrationConfirmToken.setAppUser(user);
    }

    private String buildEmailTemplate(String name, String link) {
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>";

        content = content.replace("[[name]]", name);
        content = content.replace("[[URL]]", link);
        return content;
    }

    public void enableAppUser(String username){
        AppUser foundUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        foundUser.setAccountNonExpired(true);
        foundUser.setAccountNonLocked(true);
        foundUser.setCredentialsNonExpired(true);
        foundUser.setEnabled(true);
        appUserRepo.save(foundUser);
    }
}