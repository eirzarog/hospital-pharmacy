package org.eirzarog.pharmacy.exceptions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DuplicateResourceException extends Exception{

    private HttpStatus httpStatus;
    private String message;

    public DuplicateResourceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public DuplicateResourceException(String s) {
        this.message = message;
    }
}
