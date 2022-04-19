package pl.tkaras.carworkshopwebservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppUserDetailsNotFoundException extends RuntimeException {

    public AppUserDetailsNotFoundException(String username) {
        super(String.format("role and expiration information for user %s not found", username));
    }

    public AppUserDetailsNotFoundException(Long id) {
        super(String.format("role and expiration information for id %s not found", id));
    }
}