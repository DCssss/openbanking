package by.openbanking.openbankingservice.exception;

import by.openbanking.openbankingservice.models.accinfo.OBError;
import by.openbanking.openbankingservice.models.accinfo.OBErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

@ControllerAdvice
public final class RestResponseExceptionHandler {

    private static final String HIGH_LEVEL_CODE_PATTERN = "%d %s";

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final MethodArgumentNotValidException ex
    ) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        final OBErrorResponse response = new OBErrorResponse();
        response.setCode(createHighLevelCode(httpStatus));
        response.setId(UUID.randomUUID().toString());

        final OBError error = new OBError();
        error.setErrorCode(OBErrorCode.BY_NBRB_FIELD_INVALID.getValue());
        error.setMessage(ex.getMessage());

        response.getErrors().add(error);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final ConstraintViolationException ex
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

    @ExceptionHandler(value = {OBException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final OBException ex
    ) {
        final HttpStatus httpStatus = ex.getErrorCode().getHttpStatus();

        final OBErrorResponse response = new OBErrorResponse();
        response.setCode(createHighLevelCode(httpStatus));
        response.setId(UUID.randomUUID().toString());

        final OBError error = new OBError();
        error.setErrorCode(ex.getErrorCode().getValue());
        error.setMessage(ex.getMessage());
        error.setPath(ex.getPath());
        error.setUrl(ex.getUrl());

        response.getErrors().add(error);

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final EntityNotFoundException ex
    ) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        final OBErrorResponse response = new OBErrorResponse();
        response.setCode(createHighLevelCode(httpStatus));
        response.setId(UUID.randomUUID().toString());

        final OBError error = new OBError();
        error.setErrorCode(OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND.getValue());
        error.setMessage(ex.getMessage());

        response.getErrors().add(error);

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {JpaObjectRetrievalFailureException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final JpaObjectRetrievalFailureException ex
    ) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        final OBErrorResponse response = new OBErrorResponse();
        response.setCode(createHighLevelCode(httpStatus));
        response.setId(UUID.randomUUID().toString());

        final OBError error = new OBError();
        error.setErrorCode(OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND.getValue());
        error.setMessage(ex.getMessage());

        response.getErrors().add(error);

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final Throwable ex
    ) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        final OBErrorResponse response = new OBErrorResponse();
        response.setCode(createHighLevelCode(httpStatus));
        response.setId(UUID.randomUUID().toString());

        final OBError error = new OBError();
        error.setErrorCode(OBErrorCode.BY_NBRB_UNEXPECTED_ERROR.getValue());
        error.setMessage(ex.getMessage());

        response.getErrors().add(error);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String createHighLevelCode(final HttpStatus httpStatus) {
        return String.format(HIGH_LEVEL_CODE_PATTERN, httpStatus.value(), httpStatus.getReasonPhrase());
    }

}
