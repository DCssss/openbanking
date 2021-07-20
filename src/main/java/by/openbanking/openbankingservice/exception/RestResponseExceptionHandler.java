package by.openbanking.openbankingservice.exception;

import by.openbanking.openbankingservice.models.OBErrorResponse1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<OBErrorResponse1> handleConflict(
            final RuntimeException ex,
            final WebRequest request
    ) {
        OBErrorResponse1 response = new OBErrorResponse1();
        response.setCode("code1");
        response.setId("100500");
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
