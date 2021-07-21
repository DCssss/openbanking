package by.openbanking.openbankingservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OBError {

    INVALID_API_KEY("code1", "Invalid ApiKey");

    private final String code;
    private final String message;
}
