package pl.tkaras.carworkshopwebservice.repositories;

import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;

import java.util.List;
import java.util.Optional;


public interface AppUserDetailsRepository {

    List<AppUserDetails> findAll();
    Optional<AppUserDetails> findByAppUserId(Long id);
    AppUserDetails save(AppUserDetails appUserDetails);
    void deleteById(Long id);
}