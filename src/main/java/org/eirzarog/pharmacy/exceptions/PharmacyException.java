package org.eirzarog.pharmacy.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PharmacyException extends Exception{

    private HttpStatus httpStatus;

    public PharmacyException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
