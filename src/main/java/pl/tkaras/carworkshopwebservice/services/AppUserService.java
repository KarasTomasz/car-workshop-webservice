package pl.tkaras.carworkshopwebservice.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserDetailsNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repositories.AppUserDetailsRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepo;

    private final AppUserDetailsRepository appUserDetailsRepo;

    private final PasswordEncoder passwordEncoder;

    private final RegistrationConfirmTokenService registrationConfirmTokenService;

    public AppUserService(AppUserRepository appUserRepo,
                          AppUserDetailsRepository appUserDetailsRepo,
                          PasswordEncoder passwordEncoder,
                          RegistrationConfirmTokenService registrationConfirmTokenService) {
        this.appUserRepo = appUserRepo;
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.passwordEncoder = passwordEncoder;
        this.registrationConfirmTokenService = registrationConfirmTokenService;
    }

    public List<AppUser> getAllUsers(){
        return appUserRepo.findAll();
    }

    public AppUser getUserById(Long id){
        return appUserRepo.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
    }

    public AppUser getUser(String username){
        return appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));
    }

    public List<AppUserDetails> getAllUsersDetails(){
        return appUserDetailsRepo.findAll();
    }

    public AppUserDetails getUserDetail(String username){
        Long id = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username))
                .getId();

        return appUserDetailsRepo.findByAppUserId(id)
                .orElseThrow(() -> new AppUserDetailsNotFoundException(username));
    }

    @Transactional
    public String signUp(AppUser appUser){

        if(!appUserRepo.existsByUsername(appUser.getUsername())){

            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

            String token = UUID.randomUUID().toString(); //generate register confirmation token

            appUserRepo.save(appUser);

            AppUserDetails userDetails = new AppUserDetails().builder()
                    .appUser(appUser)
                    .build();

            appUserDetailsRepo.save(userDetails);

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

    public AppUser updateUser(AppUser appUser){
        AppUser foundCarAppUser = appUserRepo.findByUsername(appUser.getUsername())
                .orElseThrow(() -> new AppUserNotFoundException(appUser.getUsername()));


        foundCarAppUser.setEmail(appUser.getEmail());
        foundCarAppUser.setFirstname(appUser.getFirstname());
        foundCarAppUser.setLastname(appUser.getLastname());
        foundCarAppUser.setStreet(appUser.getStreet());
        foundCarAppUser.setZipCode(appUser.getZipCode());
        foundCarAppUser.setCity(appUser.getCity());

        return appUserRepo.save(foundCarAppUser);
    }

    public void deleteUser(Long id){
        if(appUserRepo.existsById(id)){
            appUserRepo.deleteById(id);
        }
        else {
            throw new AppUserNotFoundException(id);
        }
    }

    @Transactional
    public void enableAppUser(String username){
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));
        AppUserDetails userDetails = appUserDetailsRepo.findByAppUserId(user.getId())
                .orElseThrow(() -> new AppUserDetailsNotFoundException(user.getId()));

        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setEnabled(true);
        appUserDetailsRepo.save(userDetails);
    }

}
