package pl.tkaras.carworkshopwebservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repositories.RegistrationConfirmTokenRepository;

@RequiredArgsConstructor
@Service
public class RegistrationConfirmTokenService {

    private final RegistrationConfirmTokenRepository confirmationTokenRepo;

    public void saveConfirmationToken(RegistrationConfirmToken confirmationToken){
        confirmationTokenRepo.save(confirmationToken);
    }
}