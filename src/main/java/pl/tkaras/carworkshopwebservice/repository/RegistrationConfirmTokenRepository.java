package pl.tkaras.carworkshopwebservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistrationConfirmTokenRepository {

    boolean existsByToken(String token);
    Optional<RegistrationConfirmToken> findByToken(String token);
    RegistrationConfirmToken save(RegistrationConfirmToken confirmToken);

    @Transactional
    @Modifying
    @Query("UPDATE RegistrationConfirmToken r SET r.confirmedOn = ?2 WHERE r.token = ?1")
    boolean updateConfirmationOn(String token, LocalDateTime confirmedOn);

}
