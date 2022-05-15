package pl.tkaras.carworkshopwebservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;

import java.util.Optional;

@Repository
public interface RegistrationConfirmTokenRepository extends JpaRepository<RegistrationConfirmToken, Long> {

    Optional<RegistrationConfirmToken> findByToken(String token);
}
