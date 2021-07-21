package by.openbanking.openbankingservice.exception;

import lombok.Getter;

@Getter
public class OBException extends Throwable {

    private final OBError error;

    public OBException(final OBError error){
        super(error.getMessage());
        this.error = error;
    }

}
