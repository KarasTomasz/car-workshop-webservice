package pl.tkaras.carworkshopwebservice.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repositories.RegistrationConfirmTokenRepository;

@Repository
public interface SqlRegistrationConfirmTokenRepository extends JpaRepository<RegistrationConfirmToken, Long>, RegistrationConfirmTokenRepository {

}
