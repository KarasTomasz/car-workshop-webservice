package pl.tkaras.carworkshopwebservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingException extends RuntimeException{

    public EmailSendingException(String email){
        super(String.format("email for %s could not be sent", email));
    }
}
