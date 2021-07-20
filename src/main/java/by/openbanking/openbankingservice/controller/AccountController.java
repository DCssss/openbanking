package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.AccountsApi;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@RestController
@RequiredArgsConstructor
public final class AccountController implements AccountsApi {

    private final AccountService mAccountService;

    @Override
    public ResponseEntity<InlineResponse2001> getAccountById(
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mAccountService.getAccountById(accountId, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<InlineResponse200> getAccounts(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mAccountService.getAccounts(xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBReadBalance1> getAccountsAccountIdBalances(
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mAccountService.getAccountsAccountIdBalances(accountId, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBReadStatement2> getAccountsAccountIdStatementsStatementId(
            @Size(min = 1, max = 35) final String statementId,
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mAccountService.getAccountsAccountIdStatementsStatementId(statementId, accountId, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBReadTransaction6> getAccountsAccountIdTransactions(
            @Size(min = 1, max = 35) final String accountId,
            @Size(min = 1, max = 35) final String transactionListId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mAccountService.getAccountsAccountIdTransactions(accountId, transactionListId, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xApiKey, xAccountConsentId);
    }

    @Override
    public ResponseEntity<OBSetAccountsTransaction> setTransaction(
            @Valid final OBSetTransaction body,
            @Size(min = 1, max = 35) final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        return mAccountService.setTransaction(body, accountId, xFapiAuthDate, xFapiCustomerIpAddress, xFapiInteractionId, authorization, xApiKey, xAccountConsentId);
    }


}