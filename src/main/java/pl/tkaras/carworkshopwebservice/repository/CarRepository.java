package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> findAll();
    Car save(Car entity);
    Optional<Car> findById(Long aLong);
    void deleteById(Long aLong);

}
