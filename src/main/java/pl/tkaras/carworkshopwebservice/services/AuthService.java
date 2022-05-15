package pl.tkaras.carworkshopwebservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.EmailSendingException;
import pl.tkaras.carworkshopwebservice.exceptions.TokenNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repositories.RegistrationConfirmTokenRepository;
import pl.tkaras.carworkshopwebservice.securities.jwt.JwtUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final RegistrationConfirmTokenRepository confirmTokenRepo;

    private final AppUserService appUserService;

    private final EmailSenderService emailSenderService;

    public String login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        return (jwtUtils.getTokenPrefix() + jwtToken);
    }

    //@Transactional
    public RegistrationConfirmToken register(AppUser appUser) {

        String token = appUserService.signUp(appUser);

        if(token != null){
            try {
                String link = "http://localhost:8081/api/v1/user/confirm?token=" + token;
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

    @Transactional
    public void confirm(String token){
        RegistrationConfirmToken registrationConfirmToken = confirmTokenRepo.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(token));

        registrationConfirmToken.setConfirmedOn(LocalDateTime.now());

        appUserService.enableAppUser(registrationConfirmToken.getAppUser().getUsername());

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
}
