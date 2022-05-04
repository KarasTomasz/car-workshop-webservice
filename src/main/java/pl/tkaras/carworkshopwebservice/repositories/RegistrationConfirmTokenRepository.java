package pl.tkaras.carworkshopwebservice.repositories;

import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;

import java.util.Optional;

public interface RegistrationConfirmTokenRepository {

    Optional<RegistrationConfirmToken> findByToken(String token);
    RegistrationConfirmToken save(RegistrationConfirmToken confirmToken);
}
