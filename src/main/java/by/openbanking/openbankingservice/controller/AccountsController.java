package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.AccountsApi;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@RestController
public class AccountsController implements AccountsApi {


    private AccountsService accountsService;


    @Override
    public ResponseEntity<InlineResponse200> getAccounts(
            String xFapiAuthDate,
            String xFapiCustomerIpAddress,
            String xFapiInteractionId,
            String authorization
    ) {

        return new ResponseEntity<>(accountsService.getAccounts(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<OBReadBalance1> getAccountsAccountIdBalances(@Size(min = 1, max = 35) String accountId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<OBReadStatement2> getAccountsAccountIdStatementsStatementId(@Size(min = 1, max = 35) String statementId, @Size(min = 1, max = 35) String accountId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<OBReadTransaction6> getAccountsAccountIdTransactions(@Size(min = 1, max = 35) String accountId, @Size(min = 1, max = 35) String transactionListId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<InlineResponse2001> getAccountsById(@Size(min = 1, max = 35) String accountId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }


    @Override
    public ResponseEntity<OBSetAccountsTransaction> setTransactions(@Valid OBSetTransaction body, @NotNull @Valid Date fromBookingDateTime, @NotNull @Valid Date toBookingDateTime, @Size(min = 1, max = 35) String accountId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }
}
