package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.AppUserDetails;

import java.util.List;
import java.util.Optional;


public interface AppUserDetailsRepository {

    List<AppUserDetails> findAll();
    Optional<AppUserDetails> findByAppUserId(Long id);
    AppUserDetails save(AppUserDetails appUserDetails);
    void deleteById(Long id);
}