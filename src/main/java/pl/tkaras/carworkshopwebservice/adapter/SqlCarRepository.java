package pl.tkaras.carworkshopwebservice.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.model.entity.Car;
import pl.tkaras.carworkshopwebservice.repository.CarRepository;

@Repository
public interface SqlCarRepository extends JpaRepository<Car, Long>, CarRepository {

}
