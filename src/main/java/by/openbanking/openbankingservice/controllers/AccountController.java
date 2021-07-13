package by.openbanking.openbankingservice.controllers;

import by.openbanking.openbankingservice.api.AccountsApi;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@RestController
public final class AccountController implements AccountsApi {

    private final AccountService mAccountService;

    @Autowired
    public AccountController(final AccountService accountService) {
        this.mAccountService = accountService;
    }

    @Override
    public ResponseEntity<InlineResponse2001> getAccountById(
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        return mAccountService.getAccountById(accountId, xFapiInteractionId);
    }

    @Override
    public ResponseEntity<InlineResponse200> getAccounts(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization
    ) {
        return mAccountService.getAccounts(xFapiInteractionId);
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
    public ResponseEntity<OBSetAccountsTransaction> setTransactions(@Valid OBSetTransaction body, @NotNull @Valid Date fromBookingDateTime, @NotNull @Valid Date toBookingDateTime, @Size(min = 1, max = 35) String accountId, String xFapiAuthDate, String xFapiCustomerIpAddress, String xFapiInteractionId, String authorization) {
        return null;
    }
}