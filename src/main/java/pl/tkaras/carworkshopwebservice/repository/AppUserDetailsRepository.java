package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.AppUserDetails;

import java.util.Optional;


public interface AppUserDetailsRepository {

    Optional<AppUserDetails> findByAppUserId(Long id);
    AppUserDetails save(AppUserDetails appUserDetails);
    void deleteById(Long id);
}