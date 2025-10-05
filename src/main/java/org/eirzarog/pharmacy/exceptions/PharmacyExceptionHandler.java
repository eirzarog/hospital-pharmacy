package org.eirzarog.pharmacy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PharmacyExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFound(ResourceNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        status = ex.getHttpStatus();

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(status.value());
        errorMessage.setErrorMessage("Not Found" + ex.getMessage());
        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateResource(DuplicateResourceException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        status = ex.getHttpStatus();

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(status.value());
        errorMessage.setErrorMessage("Conflict" + ex.getMessage());
        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorMessage> handleInsufficientStock(InsufficientStockException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        status = ex.getHttpStatus();

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(status.value());
        errorMessage.setErrorMessage("Bad Request" + ex.getMessage());
        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(PharmacyException.class)
    public ResponseEntity<ErrorMessage> handlePharmacyException(PharmacyException ex) {
        HttpStatus status = ex.getHttpStatus();

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(status.value());
        errorMessage.setErrorMessage("Pharmacy error" + ex.getMessage());
        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof PharmacyException) {
            PharmacyException pharmacyException = (PharmacyException) e;
            status = pharmacyException.getHttpStatus();
        }

        e.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(status.value());
        errorMessage.setErrorMessage("Something went wrong: " + e.getMessage());


        return ResponseEntity.status(status).body(errorMessage);
    }
}
