package pl.tkaras.carworkshopwebservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(Long id){
        super(String.format("id %s not found", id));
    }
}
