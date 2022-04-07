package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
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
        if(!carRepo.existsById(id)){
            //return ResponseEntity.notFound().build();
        }
        return new CarDtoMapper().mapToDto(carRepo.findById(id).orElseThrow());
    }

    public List<CarDto> gelAllCars(){
        return carDtoMapper.mapToDtos(carRepo.findAll());
    }

    public CarDto addCar(Car entity){
        return carDtoMapper.mapToDto(carRepo.save(entity));
    }

    public ResponseEntity deleteCar(Long id){
        if(carRepo.existsById(id)){
            carRepo.deleteById(id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


}
