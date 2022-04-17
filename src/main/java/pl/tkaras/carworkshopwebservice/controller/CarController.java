package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CarService;
import pl.tkaras.carworkshopwebservice.model.dto.CarDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id){
         return carService.getCar(id)
                 .map(response -> ResponseEntity.ok().body(response))
                 .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<List<CarDto>> getAllCars(){
        return new ResponseEntity<>(carService.gelAllCars(), HttpStatus.OK);
    }

    @GetMapping("/all/{username}")
    @PreAuthorize("hasAuthority('car:read')")
    public ResponseEntity<List<CarDto>> getCarsByUsername(@PathVariable String username){
        return new ResponseEntity<>(carService.gelAllCarsByUsername(username), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('car:add')")
    public ResponseEntity<CarDto> addCar(@RequestBody CarDto cardto){
        return new ResponseEntity<>(carService.addCar(cardto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('car:update')")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id ,@RequestBody CarDto cardto){
        return ResponseEntity.ok().body(carService.updateCar(id, cardto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('car:delete')")
    public ResponseEntity deleteCar(@PathVariable Long id){
        if(carService.getCar(id).isPresent()){
            carService.deleteCar(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
