package by.openbanking.openbankingservice.controllers;

import by.openbanking.openbankingservice.api.StatementsApi;
import by.openbanking.openbankingservice.models.OBReadStatement2Post;
import by.openbanking.openbankingservice.models.OBSetStatement;
import by.openbanking.openbankingservice.service.AccountService;
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
    public ResponseEntity<OBReadStatement2Post> setStatement(
            @Valid OBSetStatement body,
            @Size(min = 1, max = 35) String accountId,
            String xFapiAuthDate,
            String xFapiCustomerIpAddress,
            String xFapiInteractionId,
            String authorization,
            String xApiKey,
            String xAccountConsentId
            ) {
        return mStatementService.setStatement(body,accountId,xFapiAuthDate,xFapiCustomerIpAddress,xFapiInteractionId,authorization,xApiKey,xAccountConsentId);
    }
}
