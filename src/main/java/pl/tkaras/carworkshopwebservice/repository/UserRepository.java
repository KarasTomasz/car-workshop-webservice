package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    User findByUsername(String username);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    List<User> findAll();
    User save(User entity);
    void deleteById(Long id);
    void deleteByUsername(String username);

}
