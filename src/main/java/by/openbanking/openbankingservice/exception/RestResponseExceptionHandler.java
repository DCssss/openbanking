package by.openbanking.openbankingservice.exception;

import by.openbanking.openbankingservice.models.OBError;
import by.openbanking.openbankingservice.models.OBErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.UUID;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<OBErrorResponse> handleMethodArgumentNotValidException (
            final MethodArgumentNotValidException ex,
            final WebRequest request
    ) {
        OBErrorResponse response = new OBErrorResponse();
        response.setCode(String.format("%d %s", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        response.setId(UUID.randomUUID().toString());
        final OBError error = new OBError();
        error.setErrorCode("BY.NBRB.Field.Invalid");
        error.setMessage(ex.getMessage());
        response.getErrors().add(error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<OBErrorResponse> handleConflict(
            final Throwable ex,
            final WebRequest request
    ) {
        OBErrorResponse response = new OBErrorResponse();
        response.setCode(String.format("%d %s", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        response.setId(UUID.randomUUID().toString());
        final OBError error = new OBError();
        error.setMessage(ex.getMessage());
        response.getErrors().add(error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
