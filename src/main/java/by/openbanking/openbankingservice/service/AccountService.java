package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.AccountEntity;
import by.openbanking.openbankingservice.entity.ConsentEntity;
import by.openbanking.openbankingservice.entity.ListTransactions;
import by.openbanking.openbankingservice.entity.StatementEntity;
import by.openbanking.openbankingservice.exception.OBErrorCode;
import by.openbanking.openbankingservice.exception.OBException;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.repository.ListTransactionRepository;
import by.openbanking.openbankingservice.repository.StatementRepository;
import by.openbanking.openbankingservice.util.AccountConverter;
import by.openbanking.openbankingservice.util.OBHttpHeaders;
import by.openbanking.openbankingservice.util.RightsController;
import by.openbanking.openbankingservice.util.StubData;
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

    private final ListTransactionRepository mListTransactionRepository;
    private final StatementRepository mStatementRepository;
    private final ConsentRepository mConsentRepository;

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

        final Optional<AccountEntity> optionalAccount =
                consent
                        .getAccounts()
                        .stream()
                        .filter(accountEntity -> accountEntity.getId().equals(Long.valueOf(accountId)))
                        .findFirst();
        if (optionalAccount.isPresent()) {
            final AccountEntity account = optionalAccount.get();
            final AccountResponseData accountResponseData = new AccountResponseData();
            accountResponseData.setAccount(Collections.singletonList(AccountConverter.toAccount(account, consent.getPermission().contains(Permission.READACCOUNTSDETAIL))));

            // TODO: 13.07.2021 Надо не забыть доделать блоки Link и Meta , пока заглушки
            final Link links = new Link();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

            final Date now = new Date();
            final Meta meta = new Meta();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            final AccountResponse respData = new AccountResponse();
            respData.setData(accountResponseData);
            respData.setLinks(links);
            respData.setMeta(meta);

            final HttpHeaders headers = new HttpHeaders();
            headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

            return new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            throw new OBException(OBErrorCode.BY_NBRB_RESOURCE_NOTFOUND, "Account not found");
        }
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
        final Link links = new Link();
        links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

        final Date now = new Date();
        final Meta meta = new Meta();
        meta.setTotalPages(1);
        meta.setFirstAvailableDateTime(now);
        meta.setLastAvailableDateTime(now);

        final AccountResponse respData = new AccountResponse();
        respData.setData(accountResponseData);
        respData.setLinks(links);
        respData.setMeta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(respData, headers, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BalanceResponse> getAccountsAccountIdBalances(
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

        final Optional<AccountEntity> optionalAccount =
                consent.getAccounts()
                        .stream()
                        .filter(accountEntity -> accountEntity.getId().equals(Long.valueOf(accountId)))
                        .findFirst();
        if (optionalAccount.isPresent()) {
            final AccountEntity account = optionalAccount.get();

            final Date now = new Date();

            final Balance balance = new Balance();
            balance.setAccountId(String.valueOf(account.getId()));
            balance.setDateTime(now);
            balance.setCurrency(account.getCurrency());
            balance.setBalanceAmount(account.getBalanceAmount().toString());

            final BalanceResponseData balanceResponseData = new BalanceResponseData();
            balanceResponseData.setBalance(Collections.singletonList(balance));

            final LinksBalance links = new LinksBalance();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

            final Meta meta = new Meta();
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            final BalanceResponse respData = new BalanceResponse();
            respData.setData(balanceResponseData);
            respData.setLinks(links);
            respData.setMeta(meta);

            final HttpHeaders headers = new HttpHeaders();
            headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

            return new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            throw new RuntimeException("There is no account or does not have permission");
        }
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
        headers.add(OBHttpHeaders.X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(OBHttpHeaders.X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(OBHttpHeaders.AUTHORIZATION, authorization);

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

            final List<by.openbanking.openbankingservice.entity.ListTransactions> listForResponse =
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
    ) {
        ResponseEntity<OBReadTransaction6> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(OBHttpHeaders.X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(OBHttpHeaders.AUTHORIZATION, authorization);


        List<ListTransactions> listForResponse = mListTransactionRepository.findListTransactionsByAccountID(accountId, transactionListId);
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
        headers.add(OBHttpHeaders.X_FAPI_AUTH_DATE, xFapiAuthDate);
        headers.add(OBHttpHeaders.X_FAPI_CUSTOMER_IP_ADDRESS, xFapiCustomerIpAddress);
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(OBHttpHeaders.AUTHORIZATION, authorization);


        List<StatementEntity> listForResponse = mStatementRepository.findStatementsByAccountID(accountId, statementId);
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
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/" + accountId + "/statements/" + statementId);

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

