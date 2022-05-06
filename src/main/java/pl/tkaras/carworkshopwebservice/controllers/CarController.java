package pl.tkaras.carworkshopwebservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.CarMapper;
import pl.tkaras.carworkshopwebservice.services.CarService;
import pl.tkaras.carworkshopwebservice.models.dtos.CarDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<CarDto> getCar(@RequestParam("id") Long id){
        CarDto carDto = carMapper.mapToDto(carService.getCar(id));
         return new ResponseEntity<>(carDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<List<CarDto>> getAllCars(){
        List<Car> cars = carService.gelAllCars();
        return new ResponseEntity<>(carMapper.mapToDtos(cars), HttpStatus.OK);
    }

    @GetMapping("/username/all")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<List<CarDto>> getCarsByUsername(@RequestParam("username") String username){
        List<Car> cars = carService.gelAllCarsByUsername(username);
        return new ResponseEntity<>(carMapper.mapToDtos(cars), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('car:add')")
    public ResponseEntity<CarDto> addCar(@RequestBody CarDto cardto){
        Car car = carService.addCar(carMapper.mapToEntity(cardto));
        return new ResponseEntity<>(carMapper.mapToDto(car), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('car:update')")
    public ResponseEntity<CarDto> updateCar(@RequestParam("id") Long id ,@RequestBody CarDto cardto){
        Car car = carService.updateCar(id, carMapper.mapToEntity(cardto));
        return new ResponseEntity<>(carMapper.mapToDto(car), HttpStatus.OK);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('car:delete')")
    public ResponseEntity<?> deleteCar(@RequestParam("id") Long id){
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

}
