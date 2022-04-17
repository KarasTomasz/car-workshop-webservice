package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.CarDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.CarDtoMapper;
import pl.tkaras.carworkshopwebservice.model.entity.Car;
import pl.tkaras.carworkshopwebservice.repository.CarRepository;

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
                .orElseThrow(() -> new IllegalStateException(String.format("id %s not found", id)));
        return Optional.ofNullable( new CarDtoMapper().mapToDto(car));
    }

    public List<CarDto> gelAllCars(){
        return carDtoMapper.mapToDtos(carRepo.findAll());
    }

    public List<CarDto> gelAllCarsByUsername(String username){
        AppUser appUser = carRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(String.format("user %s not found", username)));

        return carDtoMapper.mapToDtos(carRepo.findAllByAppUserId(appUser.getId()));
    }

    public CarDto addCar(CarDto cardto){
            Car car = new Car().builder()
                    .createdOn(LocalDateTime.now())
                    .mark(cardto.getMark())
                    .model(cardto.getModel())
                    .description(cardto.getDescription())
                    .appUser(carRepo.findByUsername(cardto.getUsername())
                            .orElseThrow(() -> new IllegalStateException(String.format("User %s not found", cardto.getUsername()))))
                    .build();

        return carDtoMapper.mapToDto(carRepo.save(car));
    }

    public CarDto updateCar(Long id, CarDto cardto){
        if(carRepo.existsById(id)){
            Car car = carRepo.findById(id)
                    .orElseThrow(() -> new IllegalStateException(String.format("id %s not found", id)));

            car.setModel(cardto.getMark());
            car.setModel(cardto.getModel());
            car.setDescription(cardto.getDescription());
            car.setCreatedOn(car.getCreatedOn());
            car.setUpdatedOn(LocalDateTime.now());
            car.setAppUser(car.getAppUser());

            carRepo.save(car);
            return carDtoMapper.mapToDto(car);
        }
        else {
            throw new IllegalStateException(String.format("id %s not found", id));
        }
    }

    public void deleteCar(Long id){
        if(carRepo.existsById(id)){
            carRepo.deleteById(id);
        }
        else{
            throw new IllegalStateException(String.format("id %s not found", id));
        }
    }

}
