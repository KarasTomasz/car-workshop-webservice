package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CarService;
import pl.tkaras.carworkshopwebservice.model.dto.CarDto;
import pl.tkaras.carworkshopwebservice.model.entity.Car;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('car:read')")
    public List<CarDto> getAllCars(){
        return carService.gelAllCars();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('car:read')")
    public CarDto getCar(@PathVariable Long id){
        return carService.getSingleCar(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('car:add')")
    public CarDto addCar(@RequestBody Car entity){
        return carService.addCar(entity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('car:delete')")
    public ResponseEntity deleteCar(@PathVariable Long id){
        return carService.deleteCar(id);
    }



}
