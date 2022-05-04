package pl.tkaras.carworkshopwebservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.services.CarService;
import pl.tkaras.carworkshopwebservice.models.dtos.CarDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<CarDto> getCar(@RequestParam("id") Long id){
         return carService.getCar(id)
                 .map(response -> ResponseEntity.ok().body(response))
                 .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<List<CarDto>> getAllCars(){
        return new ResponseEntity<>(carService.gelAllCars(), HttpStatus.OK);
    }

    @GetMapping("/username/all")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<List<CarDto>> getCarsByUsername(@RequestParam("username") String username){
        return new ResponseEntity<>(carService.gelAllCarsByUsername(username), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('car:add')")
    public ResponseEntity<CarDto> addCar(@RequestBody CarDto cardto){
        return new ResponseEntity<>(carService.addCar(cardto), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('car:update')")
    public ResponseEntity<CarDto> updateCar(@RequestParam("id") Long id ,@RequestBody CarDto cardto){
        return ResponseEntity.ok().body(carService.updateCar(id, cardto));
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('car:delete')")
    public ResponseEntity<Object> deleteCar(@RequestParam("id") Long id){
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

}
