package pl.tkaras.carworkshopwebservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistrationConfirmTokenRepository {

    Optional<RegistrationConfirmToken> findByToken(String token);
    RegistrationConfirmToken save(RegistrationConfirmToken confirmToken);
}
