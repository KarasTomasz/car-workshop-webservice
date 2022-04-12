package pl.tkaras.carworkshopwebservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository {

    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByUsername(String username);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    List<AppUser> findAll();
    AppUser save(AppUser entity);
    void deleteById(Long id);
    void deleteByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser au SET " +
            "au.isEnabled = true, " +
            "au.isAccountNonExpired = true, " +
            "au.isAccountNonLocked = true," +
            "au.isCredentialsNonExpired = true" +
            " WHERE au.username = ?1")
    void enableAppUser(String username);

}
