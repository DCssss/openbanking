package openbankingservice.exception;

import openbankingservice.models.accinfo.OBError;
import openbankingservice.models.accinfo.OBErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
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
        return createResponse(
                HttpStatus.BAD_REQUEST,
                OBErrorCode.BY_NBRB_FIELD_INVALID.getValue(),
                ex.getMessage(),
                null,
                null
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final ConstraintViolationException ex
    ) {
        return createResponse(
                HttpStatus.BAD_REQUEST,
                OBErrorCode.BY_NBRB_FIELD_INVALID.getValue(),
                ex.getMessage(),
                null,
                null
        );
    }

    @ExceptionHandler(value = {OBException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final OBException ex
    ) {
        return createResponse(
                ex.getErrorCode().getHttpStatus(),
                ex.getErrorCode().getValue(),
                ex.getMessage(),
                ex.getPath(),
                ex.getUrl()
        );
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final EntityNotFoundException ex
    ) {
        return createResponse(
                HttpStatus.BAD_REQUEST,
                OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND.getValue(),
                ex.getMessage(),
                null,
                null
        );
    }

    @ExceptionHandler(value = {JpaObjectRetrievalFailureException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final JpaObjectRetrievalFailureException ex
    ) {
        return createResponse(
                HttpStatus.BAD_REQUEST,
                OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND.getValue(),
                ex.getMessage(),
                null,
                null
        );
    }

    @ExceptionHandler(value = {MissingRequestHeaderException.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final MissingRequestHeaderException ex
    ) {
        return createResponse(
                HttpStatus.BAD_REQUEST,
                OBErrorCode.BY_NBRB_HEADER_MISSING.getValue(),
                ex.getMessage(),
                ex.getHeaderName(),
                null
        );
    }

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<OBErrorResponse> handle(
            final Throwable ex
    ) {
        return createResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                OBErrorCode.BY_NBRB_UNEXPECTED_ERROR.getValue(),
                ex.getMessage(),
                null,
                null
        );
    }

    private ResponseEntity<OBErrorResponse> createResponse(
            final HttpStatus httpStatus,
            final String errorCode,
            final String errorMessage,
            final String path,
            final String url
    ) {
        final OBErrorResponse response = new OBErrorResponse();
        response.setCode(createHighLevelCode(httpStatus));
        response.setId(UUID.randomUUID().toString());
        response.setMessage("Невалидные параметры запроса");

        final OBError error = new OBError();
        error.setErrorCode(errorCode);
        error.setMessage(errorMessage);
        error.setPath(path);
        error.setUrl(url);

        response.getErrors().add(error);

        return new ResponseEntity<>(response, httpStatus);
    }

    private String createHighLevelCode(final HttpStatus httpStatus) {
        return String.format(HIGH_LEVEL_CODE_PATTERN, httpStatus.value(), httpStatus.getReasonPhrase());
    }

}
