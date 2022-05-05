package pl.tkaras.carworkshopwebservice.services;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repositories.RegistrationConfirmTokenRepository;

@Service
public class RegistrationConfirmTokenService {

    private final RegistrationConfirmTokenRepository confirmationTokenRepo;

    public RegistrationConfirmTokenService(RegistrationConfirmTokenRepository confirmationTokenRepo) {
        this.confirmationTokenRepo = confirmationTokenRepo;
    }

    public void saveConfirmationToken(RegistrationConfirmToken confirmationToken){
        confirmationTokenRepo.save(confirmationToken);
    }
}
