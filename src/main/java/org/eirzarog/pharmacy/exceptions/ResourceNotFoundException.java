package org.eirzarog.pharmacy.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResourceNotFoundException extends Exception{


    private HttpStatus httpStatus;
    private String message;

        public ResourceNotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public ResourceNotFoundException(String message) {
            this.message = message;
    }
}
