package pl.tkaras.carworkshopwebservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByAppUserId(Long id);

    @Query("SELECT au FROM AppUser au WHERE au.username = ?1")
    Optional<AppUser> findByUsername(String username);
}