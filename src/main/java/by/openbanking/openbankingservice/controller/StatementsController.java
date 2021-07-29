package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.accinfo.StatementsApi;
import by.openbanking.openbankingservice.models.accinfo.StatementRequest;
import by.openbanking.openbankingservice.models.accinfo.StatementResponse;
import by.openbanking.openbankingservice.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
public class StatementsController implements StatementsApi {

    private final StatementService mStatementService;

    @Autowired
    public StatementsController(final StatementService statementService) {
        this.mStatementService = statementService;
    }


    @Override
    public ResponseEntity<StatementResponse> setStatement(
            @Valid final StatementRequest body,
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mStatementService.setStatement(
                body,
                accountId,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xApiKey,
                xAccountConsentId);
    }
}
