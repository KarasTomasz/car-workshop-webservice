package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> findAll();
    Optional<Car> findById(Long aLong);
    boolean existsById(Long id);
    Car save(Car entity);
    void deleteById(Long aLong);

}
