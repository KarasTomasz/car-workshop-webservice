package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.CarDto;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.CarDtoMapper;
import pl.tkaras.carworkshopwebservice.model.entity.Car;
import pl.tkaras.carworkshopwebservice.repository.CarRepository;

import java.util.List;

@Service
public class CarService {

    private CarRepository carRepo;
    private CarDtoMapper carDtoMapper;

    private CarService(CarRepository carRepo) {
        this.carRepo = carRepo;
    }

    public CarDto getSingleCar(Long id){
        if(carRepo.existsById(id)){
            return new CarDtoMapper().mapToDto(carRepo.findById(id)
                    .orElseThrow(() -> new IllegalStateException(String.format("Problem with given id %s", id))));
        }
        throw new UsernameNotFoundException(String.format("User with id %s not found", id));
    }

    public List<CarDto> gelAllCars(){
        return carDtoMapper.mapToDtos(carRepo.findAll());
    }

    public ResponseEntity addCar(Car entity){
        if(carRepo.existsById(entity.getId())){
            carDtoMapper.mapToDto(carRepo.save(entity));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteCar(Long id){
        if(carRepo.existsById(id)){
            carRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
