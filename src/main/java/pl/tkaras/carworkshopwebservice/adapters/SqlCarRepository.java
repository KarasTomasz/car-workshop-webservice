package pl.tkaras.carworkshopwebservice.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.repositories.CarRepository;

@Repository
public interface SqlCarRepository extends JpaRepository<Car, Long>, CarRepository {

}
