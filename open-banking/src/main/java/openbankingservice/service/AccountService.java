package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.*;
import openbankingservice.data.repository.AccountRepository;
import openbankingservice.data.repository.ClientRepository;
import openbankingservice.data.repository.TransactionListRepository;
import openbankingservice.data.repository.TransactionRepository;
import openbankingservice.exception.OBErrorCode;
import openbankingservice.exception.OBException;
import openbankingservice.models.accinfo.*;
import openbankingservice.util.AccountConverter;
import openbankingservice.util.OBHttpHeaders;
import openbankingservice.util.TransactionConverter;
import openbankingservice.util.TransactionListConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

import static openbankingservice.util.OBHttpHeaders.X_ACCOUNT_CONSENT_ID;
import static openbankingservice.util.OBHttpHeaders.X_FAPI_INTERACTION_ID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final TransactionListRepository mListTransactionRepository;
    private final TransactionRepository mTransactionRepository;
    private final AccountRepository mAccountRepository;
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

       final Link links = new Link()
                .self("https://paymentapi.st.by:8243/open-banking/v1.0/accounts/" + accountId);

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(getFirstTransactionDate(account.getTransactionList()))
                .lastAvailableDateTime(getLastTransactionDate(account.getTransactionList()));

        final AccountResponse response = new AccountResponse()
                .data(accountResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

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

        final Link links = new Link()
                .self("https://paymentapi.st.by:8243/open-banking/v1.0/accounts");
        Date now = new Date();

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final AccountResponse respData = new AccountResponse()
                .data(accountResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

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
        final List<BalanceCreditLine> creditLine = new ArrayList<>();
        final Balance balance = new Balance()
                .accountId(String.valueOf(account.getId()))
                .creditDebitIndicator(account.getBalanceAmount().compareTo(BigDecimal.ZERO) > 0 ? OBCreditDebitCode2.CREDIT : OBCreditDebitCode2.DEBIT)
                .type(BalanceType.OPENINGAVAILABLE)
                .dateTime(now)
                .currency(account.getCurrency())
                .balanceAmount(account.getBalanceAmount().toString())
                .balanceEquivalentAmount(account.getBalanceAmount().toString())
                .creditLine(creditLine);

        final BalanceResponseData balanceResponseData = new BalanceResponseData()
                .balance(Collections.singletonList(balance));

        final LinksBalance links = new LinksBalance()
                .self("https://paymentapi.st.by:8243/open-banking/v1.0/balances/"+accountId);

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(getFirstTransactionDate(account.getTransactionList()))
                .lastAvailableDateTime(getLastTransactionDate(account.getTransactionList()));

        final BalanceResponse respData = new BalanceResponse()
                .data(balanceResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

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

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
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
                .self("https://paymentapi.st.by:8243/open-banking/v1.0/accounts/" + accountId + "/transactions");


        final MetaGetTransaction meta = new MetaGetTransaction()
                .totalPages(1)
                .firstAvailableDateTime(getFirstTransactionDate(transactions))
                .lastAvailableDateTime(getLastTransactionDate(transactions));

        final OBReadDataTransaction6 data = new OBReadDataTransaction6()
                .transactionListId(transactionList.getId().toString())
                .accountId(transactionList.getAccount().getId().toString())
                .fromBookingDateTime(transactionList.getFromBookingTime())
                .toBookingDateTime(transactionList.getToBookingTime())
                .creationDateTime(transactionList.getCreateTime())
                .transaction(TransactionConverter.toOBTransaction6(transactions));

        final OBReadTransaction6 response = new OBReadTransaction6()
                .data(data)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

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
                .self("https://paymentapi.st.by:8243/open-banking/v1.0/accounts/" + accountId + "/transactions/" + statementId);

        final Date now = new Date();
        List<TransactionEntity> transactions = new ArrayList<>();
        transactions.addAll(statement.getTransactions());

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(getFirstTransactionDate(transactions))
                .lastAvailableDateTime(getLastTransactionDate(transactions));

        final OBReadStatement2 response = new OBReadStatement2()
                .data(obReadDataStatement2)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApiKey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    //???????????????? ?????????????? ???? ??????????. ???????? ???????????? 1000, ???? ?????????????????? 100.000
    @Transactional
    public void checkFunds(String accountId, String currency) {
        // TODO: 13.08.2021 ???????? ???????????????? ?????????????????????? ???????? ?? ???????????? ?????????? ????????????, ?? ???????????? ?????????????? ?????? ????????????????????. ?????? ???????? ???????? ????????????????. 
        final AccountEntity account = mAccountRepository.getById(Long.valueOf(accountId));

        if (account.getBalanceAmount().doubleValue() < 1000) {
            BigDecimal amount = new BigDecimal(100000.00);
            account.setBalanceAmount(account.getBalanceAmount().add(amount));
            mAccountRepository.save(account);

            TransactionEntity transaction = new TransactionEntity();
            Date now = new Date();
            transaction.setAmount(amount);
            transaction.setCreditAccIdentification(account.getIdentification());
            transaction.setCreditBankIdentification(String.valueOf(account.getClient().getBank().getIdentifier()));
            transaction.setCreditBankName(account.getClient().getBank().getName());

            switch (currency) {
                case "BYN":
                    transaction.setDebitAccIdentification("BY01NBRB81990000000000000933");
                    break;
                case "EUR":
                    transaction.setDebitAccIdentification("BY02NBRB81990000000000000978");
                    break;
                case "USD":
                    transaction.setDebitAccIdentification("BY03NBRB81990000000000000840");
                    break;
            }

            transaction.setDebitBankName("???????????????????????? ???????? ????");
            transaction.setDebitTaxIdentification("NBRBBY2X");
            transaction.setDebitBankIdentification("NBRBBY2X");
            transaction.setDebitName("???????????????????????? ???????? ????");
            transaction.setDetails("???????????????????? ??????????");
            transaction.setCurrency(currency);
            transaction.setBookingTime(now);
            transaction.setCreditDebitIndicator(OBCreditDebitCode1.CREDIT);
            transaction.setCreditName(account.getClient().getName());
            transaction.setCreditTaxIdentification(account.getClient().getTax());
            transaction.setNumber(String.valueOf(900 + (int) (Math.random() * (200 + 1)) - 100));
            transaction.setAccount(account);
            mTransactionRepository.save(transaction);
        }
    }

    private Date getFirstTransactionDate(List<TransactionEntity> transactions) {
        Date firstDate = new Date();

        if (!transactions.isEmpty()){
            firstDate = transactions.stream().map(TransactionEntity::getBookingTime).min(Date::compareTo).get();
        }
        return firstDate;
    }

    private Date getLastTransactionDate(List<TransactionEntity> transactions) {
        Date lastDate = new Date();

        if (!transactions.isEmpty()){
            lastDate = transactions.stream().map(TransactionEntity::getBookingTime).max(Date::compareTo).get();
        }
        return lastDate;
    }

}

