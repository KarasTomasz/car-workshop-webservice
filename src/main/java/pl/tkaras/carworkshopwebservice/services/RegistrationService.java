package pl.tkaras.carworkshopwebservice.services;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.EmailSendingException;
import pl.tkaras.carworkshopwebservice.exceptions.TokenNotFoundException;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDto;
import pl.tkaras.carworkshopwebservice.models.dtos.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.RegistrationConfirmTokenDtoMapper;
import pl.tkaras.carworkshopwebservice.repositories.RegistrationConfirmTokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final RegistrationConfirmTokenRepository confirmTokenRepo;
     private final AppUserService appUserService;
     private final RegistrationConfirmTokenDtoMapper confirmTokenDtoMapper;
    private final EmailSenderService emailSenderService;


    public RegistrationService(RegistrationConfirmTokenRepository confirmTokenRepo, AppUserService appUserService, RegistrationConfirmTokenDtoMapper confirmTokenDtoMapper, EmailSenderService emailSenderService) {
        this.confirmTokenRepo = confirmTokenRepo;
        this.appUserService = appUserService;
        this.confirmTokenDtoMapper = confirmTokenDtoMapper;
        this.emailSenderService = emailSenderService;
    }

    //@Transactional
    public RegistrationConfirmTokenDto register(AppUserDto appUserdto) {

        String token = appUserService.signUp(appUserdto);

        if(token != null){
            try {
                String link = "http://localhost:8081/api/v1/user/confirm?token=" + token;
                emailSenderService.send(appUserdto.getEmail(), buildEmailTemplate(appUserdto.getFirstname(), link), true);
            }
            catch (Exception e){
                throw new EmailSendingException(appUserdto.getEmail());
            }
            return confirmTokenDtoMapper.mapToDto(confirmTokenRepo.findByToken(token)
                    .orElseThrow(() -> new TokenNotFoundException(token)));
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
