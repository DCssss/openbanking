package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.*;
import by.openbanking.openbankingservice.exception.OBErrorCode;
import by.openbanking.openbankingservice.exception.OBException;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.repository.TransactionListRepository;
import by.openbanking.openbankingservice.repository.StatementRepository;
import by.openbanking.openbankingservice.repository.TransactionRepository;
import by.openbanking.openbankingservice.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

import static by.openbanking.openbankingservice.util.OBHttpHeaders.X_FAPI_INTERACTION_ID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final TransactionListRepository mListTransactionRepository;
    private final TransactionRepository mTransactionRepository;

    private final ClientService mClientService;
    private final ConsentService mConsentService;

    @Transactional
    public ResponseEntity<AccountResponse> getAccountById(
            final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/accounts/{accountId}");
        final AccountEntity account = consent.getAccount(Long.valueOf(accountId));
        final AccountResponseData accountResponseData = new AccountResponseData();
        accountResponseData.setAccount(Collections.singletonList(AccountConverter.toAccount(account, consent.getPermission().contains(Permission.READACCOUNTSDETAIL))));

        // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
        final Link links = new Link()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

        final Date now = new Date();

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final AccountResponse response = new AccountResponse()
                .data(accountResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<AccountResponse> getAccounts(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {

        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/accounts");

        final AccountResponseData accountResponseData = new AccountResponseData();
        accountResponseData.setAccount(AccountConverter.toAccount(consent.getAccounts(), consent.getPermission().contains(Permission.READACCOUNTSDETAIL)));

        // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
        final Link links = new Link()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

        final Date now = new Date();

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final AccountResponse respData = new AccountResponse()
                .data(accountResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(respData, headers, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BalanceResponse> getAccountBalanceById(
            @Size(min = 1, max = 35) String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/accounts/{accountId}");
        final AccountEntity account = consent.getAccount(Long.valueOf(accountId));

        final Date now = new Date();

        final Balance balance = new Balance()
                .accountId(String.valueOf(account.getId()))
                .dateTime(now)
                .currency(account.getCurrency())
                .balanceAmount(account.getBalanceAmount().toString());

        final BalanceResponseData balanceResponseData = new BalanceResponseData()
                .balance(Collections.singletonList(balance));

        final LinksBalance links = new LinksBalance()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final BalanceResponse respData = new BalanceResponse()
                .data(balanceResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(respData, headers, HttpStatus.OK);
    }

    @Transactional
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
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/accounts/{accountId}");
        final AccountEntity account = consent.getAccount(Long.valueOf(accountId));

        final Date now = new Date();

        final TransactionListEntity transactionList = new TransactionListEntity();
        transactionList.setCreateTime(now);
        transactionList.setAccount(account);
        transactionList.setFromBookingTime(body.getData().getTransaction().getFromBookingDateTime());
        transactionList.setToBookingTime(body.getData().getTransaction().getToBookingDateTime());
        mListTransactionRepository.save(transactionList);

        final Links links = new Links()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId + "/transactions");

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final OBSetAccountsTransactionData data = new OBSetAccountsTransactionData()
                .transaction(TransactionListConverter.toOBSetAccountsTransAction1(transactionList));

        final OBSetAccountsTransaction response = new OBSetAccountsTransaction()
                .data(data)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
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
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/accounts/{accountId}");
        final AccountEntity account = consent.getAccount(Long.valueOf(accountId));

        final Optional<TransactionListEntity> optionalTransactionList =
                account.getTransactionLists()
                        .stream()
                        .filter(transactionList -> transactionList.getId().equals(Long.valueOf(transactionListId)))
                        .findFirst();

        if (!optionalTransactionList.isPresent()) {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND, "Transaction list not found");
        }

        final TransactionListEntity transactionList = optionalTransactionList.get();

        final List<TransactionEntity> transactions = mTransactionRepository.findAllByBookingTimeBetween(transactionList.getFromBookingTime(), transactionList.getToBookingTime());

        final LinkGetTransaction links = new LinkGetTransaction()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId + "/transactions");

        final Date now = new Date();

        final MetaGetTransaction meta = new MetaGetTransaction()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final OBReadDataTransaction6 data = new OBReadDataTransaction6()
                .transactionListId(transactionList.getId().toString())
                .transaction(TransactionConverter.toOBTransaction6(transactions));

        final OBReadTransaction6 response = new OBReadTransaction6()
                .data(data)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
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
        mClientService.identifyClient(xApiKey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/accounts/{accountId}/statements/{statementId}");
        final AccountEntity account = consent.getAccount(Long.valueOf(accountId));

        final Optional<StatementEntity> optionalStatement =
                account.getStatements()
                        .stream()
                        .filter(statementEntity -> statementEntity.getId().equals(Long.valueOf(statementId)))
                        .findFirst();

        if (!optionalStatement.isPresent()) {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND, "Statement not found");
        }

        final StatementEntity statement = optionalStatement.get();

        final OpavBalance opavBalance = new OpavBalance()
                .amount("100.00")
                .currency("BYN")
                .equivalentAmount("100.0000")
                .creditDebitIndicator(OBCreditDebitCode1.CREDIT);

        final ClavBalance clavBalance = new ClavBalance()
                .amount("5.00")
                .currency("BYN")
                .equivalentAmount("5.0000")
                .creditDebitIndicator(OBCreditDebitCode1.CREDIT);

        final List<OBTransaction1> listTransact = new ArrayList<>(TransactionConverter.toOBTransaction1(statement.getTransactions()));

        final OBStatement2 obStatement2 = new OBStatement2()
                .accountId(statement.getAccount().getId().toString())
                .statementId(statement.getId().toString())
                .fromBookingDate(statement.getFromBookingDate())
                .toBookingDate(statement.getToBookingDate())
                .creationDateTime(statement.getCreateTime())
                .opavBalance(opavBalance)
                .clavBalance(clavBalance)
                .transaction(listTransact);

        final OBReadDataStatement2 obReadDataStatement2 = new OBReadDataStatement2()
                .statement(Collections.singletonList(obStatement2));

        final LinksStatementGet links = new LinksStatementGet()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId + "/statements/" + statementId);

        final Date now = new Date();

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final OBReadStatement2 response = new OBReadStatement2()
                .data(obReadDataStatement2)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}

