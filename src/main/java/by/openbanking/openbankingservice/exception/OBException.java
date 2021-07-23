package by.openbanking.openbankingservice.exception;

import lombok.Getter;

@Getter
public final class OBException extends RuntimeException {

    private final OBErrorCode errorCode;
    private final String path;
    private final String url;

    /*
        private final OBError error;

        public OBException(final OBError error){
            super(error.getMessage());
            this.error = error;
        }
    */
    public OBException(
            final OBErrorCode errorCode,
            final String message,
            final String path,
            final String url
    ) {
        super(message);
        this.errorCode = errorCode;
        this.path = path;
        this.url = url;
    }

    public OBException(
            final OBErrorCode errorCode,
            final String message,
            final String path
    ) {
        this(errorCode, message, path, null);
    }

    public OBException(
            final OBErrorCode errorCode,
            final String message
    ) {
        this(errorCode, message, null, null);
    }
}
