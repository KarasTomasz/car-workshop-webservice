package pl.tkaras.carworkshopwebservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(Long id){
        super(String.format("car with id %s not found", id));
    }
}
