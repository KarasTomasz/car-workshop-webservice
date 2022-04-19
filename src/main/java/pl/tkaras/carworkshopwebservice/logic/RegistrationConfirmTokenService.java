package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.RegistrationConfirmTokenDtoMapper;
import pl.tkaras.carworkshopwebservice.repository.RegistrationConfirmTokenRepository;

@Service
public class RegistrationConfirmTokenService {

    private final RegistrationConfirmTokenRepository confirmationTokenRepo;

    public RegistrationConfirmTokenService(RegistrationConfirmTokenRepository confirmationTokenRepo, RegistrationConfirmTokenDtoMapper confirmTokenDtoMapper) {
        this.confirmationTokenRepo = confirmationTokenRepo;
    }

    public void saveConfirmationToken(RegistrationConfirmToken confirmationToken){
        confirmationTokenRepo.save(confirmationToken);
    }
}
