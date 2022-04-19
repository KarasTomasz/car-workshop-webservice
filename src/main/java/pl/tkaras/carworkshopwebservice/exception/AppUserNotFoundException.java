package pl.tkaras.carworkshopwebservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String username) {
        super(String.format("user %s not found", username));
    }

    public AppUserNotFoundException(Long id) {
        super(String.format("user with id %s not found", id));
    }
}
