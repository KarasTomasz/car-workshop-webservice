package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.RegistrationConfirmTokenDtoMapper;
import pl.tkaras.carworkshopwebservice.repository.RegistrationConfirmTokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationConfirmTokenService {

    private final RegistrationConfirmTokenRepository confirmationTokenRepo;
    private final RegistrationConfirmTokenDtoMapper confirmTokenDtoMapper;

    public RegistrationConfirmTokenService(RegistrationConfirmTokenRepository confirmationTokenRepo, RegistrationConfirmTokenDtoMapper confirmTokenDtoMapper) {
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.confirmTokenDtoMapper = confirmTokenDtoMapper;
    }

    public Optional<RegistrationConfirmToken> getConfirmationToken(String token){
        return confirmationTokenRepo.findByToken(token);
    }

    public void saveConfirmationToken(RegistrationConfirmToken confirmationToken){
        confirmationTokenRepo.save(confirmationToken);
    }

    @Transactional
    public ResponseEntity setConfirmedOn(String token){
        if(confirmationTokenRepo.updateConfirmationOn(token, LocalDateTime.now())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

   /* public ResponseEntity setConfirmedOn(String token){
        if(confirmationTokenRepo.existsByToken(token)){
            RegistrationConfirmToken tokenObject = confirmationTokenRepo.findByToken(token)
                    .orElseThrow(() -> new IllegalStateException(String.format("Problem with given token &s", token)));
            tokenObject.setConfirmedOn(LocalDateTime.now());
            confirmTokenDtoMapper.mapToDto(confirmationTokenRepo.save(tokenObject));
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build() ;
    }*/
}
