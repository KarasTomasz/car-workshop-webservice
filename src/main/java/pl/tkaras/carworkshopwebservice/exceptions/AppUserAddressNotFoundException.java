package pl.tkaras.carworkshopwebservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppUserAddressNotFoundException extends RuntimeException{

    public AppUserAddressNotFoundException(Long id) {
        super("AppUserAddress with the given id " + id + " was not found");
    }
}