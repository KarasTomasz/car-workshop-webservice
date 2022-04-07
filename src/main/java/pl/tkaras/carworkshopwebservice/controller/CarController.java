package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CarService;
import pl.tkaras.carworkshopwebservice.model.dto.CarDto;
import pl.tkaras.carworkshopwebservice.model.entity.Car;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private CarService carService;

    private CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable Long id){
        return carService.getSingleCar(id);
    }

    @GetMapping("/all")
    public List<CarDto> getAllCars(){
        return carService.gelAllCars();
    }

    @PostMapping("")
    public CarDto addCar(@RequestBody Car entity){
        return carService.addCar(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable Long id){
        return carService.deleteCar(id);
    }



}
