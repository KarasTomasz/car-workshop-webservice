package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repository.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repository.RegistrationConfirmTokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationConfirmTokenRepository confirmTokenRepo;
     private final AppUserService appUserService;
    private final EmailSenderService emailSenderService;


    public RegistrationService(RegistrationConfirmTokenRepository confirmTokenRepo, AppUserService appUserService, EmailSenderService emailSenderService) {
        this.confirmTokenRepo = confirmTokenRepo;
        this.appUserService = appUserService;
        this.emailSenderService = emailSenderService;
    }

    public ResponseEntity register(AppUser appUser) {

        String token = appUserService.signUp(appUser);

        if(token != null){
            try {

                String link = "http://localhost:8081/api/v1/user/confirm?token=" + token;
                emailSenderService.send(appUser.getEmail(), buildEmailTemplate(appUser.getFirstname(), link), true);


            }
            catch (Exception e){
                throw new IllegalStateException("sending email failed");
            }
        }

        return null;
    }

    @Transactional
    public ResponseEntity confirm(String token){
        if(confirmTokenRepo.existsByToken(token)){
            RegistrationConfirmToken registrationConfirmToken =
                    confirmTokenRepo.findByToken(token)
                                    .orElseThrow(() -> new IllegalStateException(String.format("Problem with token %s", token)));

            registrationConfirmToken.setConfirmedOn(LocalDateTime.now());

            appUserService.enableAppUser(registrationConfirmToken.getAppUser().getUsername());

            AppUser user =  registrationConfirmToken.getAppUser();
            registrationConfirmToken.setAppUser(user);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
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
