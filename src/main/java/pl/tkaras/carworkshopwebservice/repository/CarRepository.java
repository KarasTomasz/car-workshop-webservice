package pl.tkaras.carworkshopwebservice.repository;

import org.springframework.data.jpa.repository.Query;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> findById(Long id);
    List<Car> findAll();
    List<Car> findAllByAppUserId(Long id);

    @Query("SELECT au FROM AppUser au WHERE au.username = ?1")
    Optional<AppUser> findByUsername(String username);
    boolean existsById(Long id);
    Car save(Car entity);
    void deleteById(Long aLong);

}
