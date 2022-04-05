package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.entity.Car;
import pl.tkaras.carworkshopwebservice.repository.CarRepository;

import java.util.List;

@Service
public class CarService {

    private CarRepository carRepo;

    private CarService(CarRepository carRepo) {
        this.carRepo = carRepo;
    }

    public Car getSingleCar(Long id){
        return carRepo.findById(id).orElseThrow();
    }

    public List<Car> gelAllCars(){
        return carRepo.findAll();
    }

    public Car addCar(Car entity){
        return carRepo.save(entity);
    }

    public void deleteCar(Long id){
        carRepo.deleteById(id);
    }


}
