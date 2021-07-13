package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.StatementsApi;
import by.openbanking.openbankingservice.models.OBReadStatement2Post;
import by.openbanking.openbankingservice.models.OBSetStatement;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class StatementsController implements StatementsApi {
    @Override
    public ResponseEntity<OBReadStatement2Post> setStatement(@Valid OBSetStatement body, @NotNull @Valid Date fromBookingDate, @NotNull @Valid Date toBookingDate, @Size(min = 1, max = 35) String accountId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }
}
