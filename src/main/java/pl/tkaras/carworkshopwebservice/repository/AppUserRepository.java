package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository {

    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByUsername(String username);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    List<AppUser> findAll();
    AppUser save(AppUser appUser);
    void deleteById(Long id);
}
