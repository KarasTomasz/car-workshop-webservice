package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CarService;
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
    public Car getCar(@PathVariable Long id){
        return carService.getSingleCar(id);
    }

    @GetMapping("")
    public List<Car> getAllCars(){
        return carService.gelAllCars();
    }

    @PostMapping("")
    public Car addCar(@RequestBody Car entity){
        return carService.addCar(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(Long id){
        carService.deleteCar(id);
    }



}
