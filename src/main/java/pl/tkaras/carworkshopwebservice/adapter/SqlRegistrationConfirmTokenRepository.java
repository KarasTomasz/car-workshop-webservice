package pl.tkaras.carworkshopwebservice.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.repository.RegistrationConfirmTokenRepository;

@Repository
public interface SqlRegistrationConfirmTokenRepository extends JpaRepository<RegistrationConfirmToken, Long>, RegistrationConfirmTokenRepository {

}
