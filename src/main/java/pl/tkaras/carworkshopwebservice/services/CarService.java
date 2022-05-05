package pl.tkaras.carworkshopwebservice.services;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.CarNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.repositories.CarRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepo;

    public CarService(CarRepository carRepo) {
        this.carRepo = carRepo;
    }

    public Car getCar(Long id){
        return carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public List<Car> gelAllCars(){
        return carRepo.findAll();
    }

    public List<Car> gelAllCarsByUsername(String username){
        AppUser appUser = carRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        return carRepo.findAllByAppUserId(appUser.getId());
    }

    public Car addCar(Car car){
        return carRepo.save(car);
    }

    public Car updateCar(Long id, Car car){
        Car foundCar = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));

        foundCar.setModel(car.getMark());
        foundCar.setModel(car.getModel());
        foundCar.setDescription(car.getDescription());
        foundCar.setCreatedOn(foundCar.getCreatedOn());
        foundCar.setUpdatedOn(LocalDateTime.now());
        foundCar.setAppUser(foundCar.getAppUser());

        return carRepo.save(car);
      }

    public void deleteCar(Long id){
        if(carRepo.existsById(id)){
            carRepo.deleteById(id);
        }
        else{
            throw new CarNotFoundException(id);
        }
    }

}
