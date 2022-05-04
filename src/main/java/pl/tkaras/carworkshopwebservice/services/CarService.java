package pl.tkaras.carworkshopwebservice.services;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.CarNotFoundException;
import pl.tkaras.carworkshopwebservice.models.dtos.CarDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.CarDtoMapper;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.repositories.CarRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepo;
    private final CarDtoMapper carDtoMapper;

    public CarService(CarRepository carRepo, CarDtoMapper carDtoMapper) {
        this.carRepo = carRepo;
        this.carDtoMapper = carDtoMapper;
    }

    public Optional<CarDto> getCar(Long id){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
        return Optional.ofNullable( new CarDtoMapper().mapToDto(car));
    }

    public List<CarDto> gelAllCars(){
        return carDtoMapper.mapToDtos(carRepo.findAll());
    }

    public List<CarDto> gelAllCarsByUsername(String username){
        AppUser appUser = carRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        return carDtoMapper.mapToDtos(carRepo.findAllByAppUserId(appUser.getId()));
    }

    public CarDto addCar(CarDto cardto){
            Car car = new Car().builder()
                    .createdOn(LocalDateTime.now())
                    .mark(cardto.getMark())
                    .model(cardto.getModel())
                    .description(cardto.getDescription())
                    .appUser(carRepo.findByUsername(cardto.getUsername())
                            .orElseThrow(() -> new AppUserNotFoundException(cardto.getUsername())))
                    .build();

        return carDtoMapper.mapToDto(carRepo.save(car));
    }

    public CarDto updateCar(Long id, CarDto cardto){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));

        car.setModel(cardto.getMark());
        car.setModel(cardto.getModel());
        car.setDescription(cardto.getDescription());
        car.setCreatedOn(car.getCreatedOn());
        car.setUpdatedOn(LocalDateTime.now());
        car.setAppUser(car.getAppUser());

        return carDtoMapper.mapToDto(carRepo.save(car));
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
