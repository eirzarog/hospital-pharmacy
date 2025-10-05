package org.eirzarog.pharmacy.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InsufficientStockException extends Exception{

    private HttpStatus httpStatus;

    public InsufficientStockException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
