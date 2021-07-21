package by.openbanking.openbankingservice.exception;

import by.openbanking.openbankingservice.models.OBError1;
import by.openbanking.openbankingservice.models.OBErrorResponse1;
import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.UUID;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<OBErrorResponse1> handleConflict(
            final Throwable ex,
            final WebRequest request
    ) {
        OBErrorResponse1 response = new OBErrorResponse1();
        response.setCode(String.format("%d %s", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        response.setId(UUID.randomUUID().toString());
        final OBError1 error = new OBError1();
        error.setMessage(ex.getMessage());
        response.getErrors().add(error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
