package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.ListTransactions;
import by.openbanking.openbankingservice.model.Statement;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.ListTransactionRepository;
import by.openbanking.openbankingservice.repository.StatementRepository;
import by.openbanking.openbankingservice.util.AccountConverter;
import by.openbanking.openbankingservice.util.RightsController;
import by.openbanking.openbankingservice.util.StubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

@Service
public class AccountService {

    private static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    private static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private static final String AUTHORIZATION = "authorization";
    private static final String X_API_KEY = "x-api-key";
    private static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";

    private final AccountRepository mAccountRepository;
    private final ListTransactionRepository mListTransactionRepository;
    private final StatementRepository mStatementRepository;
    private final ConsentRepository mConsentRepository;

    @Autowired
    public AccountService(final AccountRepository accountRepository, ListTransactionRepository mListTransactionRepository, StatementRepository mStatementRepository, ConsentRepository mConsentRepository) {
        this.mAccountRepository = accountRepository;
        this.mListTransactionRepository = mListTransactionRepository;
        this.mStatementRepository = mStatementRepository;
        this.mConsentRepository = mConsentRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<InlineResponse2001> getAccountById(
            final String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        ResponseEntity<InlineResponse2001> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);


        final Optional<by.openbanking.openbankingservice.model.Account> accountData = mAccountRepository.findById(Long.valueOf(accountId));

        final Long clientId = StubData.CLIENTS.get(xApiKey);
        if (accountData.isPresent() && clientId != null && RightsController.isHaveRights(mConsentRepository, clientId, "/accounts/{accountId}")) {

            final Accounts accData = new Accounts();
            accData.setAccounts(Collections.singletonList(AccountConverter.toAccount(accountData.get())));

            // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
            final Links links = new Links();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

            final Date now = new Date();
            final Meta meta = new Meta();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            final InlineResponse2001 respData = new InlineResponse2001();
            respData.setData(accData);
            respData.setLinks(links);
            respData.setMeta(meta);

            responseEntity = new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
        }

        return responseEntity;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<InlineResponse200> getAccounts(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        ResponseEntity<InlineResponse200> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);

        try {

            final List<by.openbanking.openbankingservice.models.Account> accountsForResponse =
                    AccountConverter.toAccount(mAccountRepository.findAll());

            final Long clientId = StubData.CLIENTS.get(xApiKey);

            if (!accountsForResponse.isEmpty() && clientId != null && RightsController.isHaveRights(mConsentRepository, clientId, "/accounts")) {

                final Accounts accData = new Accounts();
                accData.setAccounts(accountsForResponse);

                // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
                final Link links = new Link();
                links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

                final Date now = new Date();
                final Meta meta = new Meta();
                meta.setTotalPages(1);
                meta.setFirstAvailableDateTime(now);
                meta.setLastAvailableDateTime(now);

                final InlineResponse200 respData = new InlineResponse200();
                respData.setData(accData);
                respData.setLinks(links);
                respData.setMeta(meta);

                responseEntity = new ResponseEntity<>(respData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<OBReadBalance1> getAccountsAccountIdBalances(
            @Size(min = 1, max = 35) String accountId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        ResponseEntity<OBReadBalance1> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);


        final Optional<by.openbanking.openbankingservice.model.Account> accountData = mAccountRepository.findById(Long.valueOf(accountId));
        final Long clientId = StubData.CLIENTS.get(xApiKey);

        if (accountData.isPresent() && clientId != null && RightsController.isHaveRights(mConsentRepository, clientId, "/accounts/{accountId}/balances")) {

            OBReadBalance1Data obReadBalance1Data = new OBReadBalance1Data();
            OBReadBalance1DataBalance obReadBalance1DataBalance = new OBReadBalance1DataBalance();
            List<OBReadBalance1DataBalance> listBalance = new ArrayList<>();
            listBalance.add(obReadBalance1DataBalance);
            obReadBalance1Data.setBalance(listBalance);
            final Accounts accData = new Accounts();
            accData.setAccounts(Collections.singletonList(AccountConverter.toAccount(accountData.get())));

            // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
            final LinksBalance links = new LinksBalance();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

            final Date now = new Date();
            final Meta meta = new Meta();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            final OBReadBalance1 respData = new OBReadBalance1();
            respData.setData(obReadBalance1Data);
            respData.setLinks(links);
            respData.setMeta(meta);

            responseEntity = new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
        }

        return responseEntity;
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
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);

        ResponseEntity<OBSetAccountsTransaction> response;
        try {

            final Date now = new Date();
            ListTransactions listTransactions = new ListTransactions();
            listTransactions.setListTransactionCreateTime(now);
            listTransactions.setAccountID(Long.valueOf(accountId));
            listTransactions.setListTransactionFromBookingTime(body.getData().getTransaction().stream().findFirst().get().getFromBookingDateTime());
            listTransactions.setListTransactionToBookingTime(body.getData().getTransaction().stream().findFirst().get().getToBookingDateTime());
            mListTransactionRepository.save(listTransactions);


            final OBSetAccountsTransaction responseContent = new OBSetAccountsTransaction();
            final Links links = new Links();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId + "/transactions");
            final Meta meta = new Meta();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            final List<by.openbanking.openbankingservice.model.ListTransactions> listForResponse =
                    mListTransactionRepository.findListTransactionsById(accountId);


                OBSetAccountsTransAction1 respData = new OBSetAccountsTransAction1();
                respData.setAccountId(String.valueOf(listForResponse.stream().findFirst().get().getAccountID()));
                respData.setTransactionListId(String.valueOf(listForResponse.stream().findFirst().get().getListTransactionID()));
                respData.setFromBookingDateTime(listForResponse.stream().findFirst().get().getListTransactionFromBookingTime());
                respData.setToBookingDateTime(listForResponse.stream().findFirst().get().getListTransactionToBookingTime());
                OBSetAccountsTransactionData obSetAccountsTransactionData = new OBSetAccountsTransactionData();
                List<OBSetAccountsTransAction1> listTransaction = new ArrayList<>();
                listTransaction.add(respData);
                obSetAccountsTransactionData.setTransaction(listTransaction);
                responseContent.setData(obSetAccountsTransactionData);
                responseContent.setLinks(links);
                responseContent.setMeta(meta);


            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
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
    )
    {
        ResponseEntity<OBReadTransaction6> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);


        List<ListTransactions> listForResponse = mListTransactionRepository.findListTransactionsByAccountID(accountId,transactionListId);
        final Long clientId = StubData.CLIENTS.get(xApiKey);
        if (!listForResponse.isEmpty() && clientId != null && RightsController.isHaveRights(mConsentRepository, clientId, "/accounts/{accountId}/transactions/{transactionListId}")) {

            OBReadTransaction6 dataForResponse = new OBReadTransaction6();
            OBReadDataTransaction6 obReadDataTransaction6 = new OBReadDataTransaction6();
            OBTransaction6 obTransaction6 = new OBTransaction6();
            OBTransactionCashBalance obTransactionCashBalance = new OBTransactionCashBalance();
            OBBankTransactionCodeStructure1 obBankTransactionCodeStructure1 = new OBBankTransactionCodeStructure1();
            OBTransactionCardInstrument1 obTransactionCardInstrument1 = new OBTransactionCardInstrument1();
            OBActiveChargeAmount obActiveChargeAmount = new OBActiveChargeAmount();
            OBCreditor obCreditor = new OBCreditor();
            OBCurrencyExchange5 obCurrencyExchange5 = new OBCurrencyExchange5();
            OBMerchantDetails1 obMerchantDetails1 = new OBMerchantDetails1();
            ProprietaryBankTransactionCodeStructure1 proprietaryBankTransactionCodeStructure1 = new ProprietaryBankTransactionCodeStructure1();

            obTransaction6.setBalance(obTransactionCashBalance);
            obTransaction6.setBankTransactionCode(obBankTransactionCodeStructure1);
            obTransaction6.setCardInstrument(obTransactionCardInstrument1);
            obTransaction6.setChargeAmount(obActiveChargeAmount);
            obTransaction6.setCurrencyExchange(obCurrencyExchange5);
            obTransaction6.setCreditor(obCreditor);
            obTransaction6.setMerchantDetails(obMerchantDetails1);
            obTransaction6.setProprietaryBankTransactionCode(proprietaryBankTransactionCodeStructure1);
            obTransaction6.setCreditDebitIndicator(OBCreditDebitCode1.CREDIT);
            obTransaction6.setStatus(OBEntryStatus1Code.PENDING);

            List<OBTransaction6> listTransaction6 = new ArrayList<>();
            obTransaction6.setAccountId(accountId);
            obTransaction6.setBookingDateTime(listForResponse.stream().findFirst().get().getListTransactionFromBookingTime());
           // listTransaction6.add(obTransaction6);

            obReadDataTransaction6.setTransactionListId(transactionListId);
            obReadDataTransaction6.setTransaction(listTransaction6);
            dataForResponse.setData(obReadDataTransaction6);

            // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
            final LinkGetTransaction links = new LinkGetTransaction();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId + "/transaction/" + transactionListId);

            final Date now = new Date();
            final MetaGetTransaction meta = new MetaGetTransaction();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            final OBReadTransaction6 respData = new OBReadTransaction6();
            respData.setData(obReadDataTransaction6);
            respData.setLinks(links);
            respData.setMeta(meta);

            responseEntity = new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return responseEntity;
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
        ResponseEntity<OBReadStatement2> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(AUTHORIZATION, authorization);


        List<Statement> listForResponse = mStatementRepository.findStatementsByAccountID(accountId,statementId);
        final Long clientId = StubData.CLIENTS.get(xApiKey);
        if (!listForResponse.isEmpty() && clientId != null && RightsController.isHaveRights(mConsentRepository, clientId, "/statements/accounts/{accountId}/statements/{statementId}")) {

            final OBReadStatement2 respData = new OBReadStatement2();
            OBReadDataStatement2 obReadDataStatement2 = new OBReadDataStatement2();
            OBStatement2 obStatement2 = new OBStatement2();
            OpavBalance opavBalance = new OpavBalance();
            ClavBalance clavBalance = new ClavBalance();
            OBTransaction1 obTransaction1 = new OBTransaction1();
            List<OBTransaction1> listTransact = new ArrayList<>();
            listTransact.add(obTransaction1);

            obStatement2.setAccountId(String.valueOf(listForResponse.stream().findFirst().get().getAccount().getId()));
            obStatement2.setStatementId(String.valueOf(listForResponse.stream().findFirst().get().getId()));
            obStatement2.setFromBookingDate(listForResponse.stream().findFirst().get().getFromBookingDate());
            obStatement2.setToBookingDate(listForResponse.stream().findFirst().get().getToBookingDate());
            obStatement2.setCreationDateTime(listForResponse.stream().findFirst().get().getCreateTime());
            obStatement2.setTransaction(listTransact);

            // TODO: 14.07.2021 CLAVBALANCE,OPAVBALANCE - заглушки пока не знаю откуда тянуть
            opavBalance.setAmount("100.00");
            opavBalance.setCurrency("BYN");
            opavBalance.setEquivalentAmount("100.0000");
            opavBalance.setCreditDebitIndicator(OBCreditDebitCode1.CREDIT);
            clavBalance.setAmount("5.00");
            clavBalance.setCurrency("BYN");
            clavBalance.setEquivalentAmount("5.0000");
            clavBalance.setCreditDebitIndicator(OBCreditDebitCode1.CREDIT);
            obStatement2.setOpavBalance(opavBalance);
            obStatement2.setClavBalance(clavBalance);

            List<OBStatement2> listStatement = new ArrayList<>();

            listStatement.add(obStatement2);
            obReadDataStatement2.setStatement(listStatement);

            // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
            final LinksStatementGet links = new LinksStatementGet();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId +"/statements/" + statementId);

            final Date now = new Date();
            final Meta meta = new Meta();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            respData.setData(obReadDataStatement2);
            respData.setLinks(links);
            respData.setMeta(meta);

            responseEntity = new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
}

