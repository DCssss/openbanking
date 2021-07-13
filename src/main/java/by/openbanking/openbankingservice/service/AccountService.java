package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.util.AccountConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AccountService {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    private final AccountRepository mAccountRepository;

    @Autowired
    public AccountService(final AccountRepository accountRepository) {
        this.mAccountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<InlineResponse2001> getAccountById(
            final String accountId,
            final String xFapiInteractionId
    ){
        ResponseEntity<InlineResponse2001> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        final Optional<by.openbanking.openbankingservice.model.Account> accountData = mAccountRepository.findById(Long.valueOf(accountId));

        if (accountData.isPresent()) {

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
            responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<InlineResponse200> getAccounts(
            final String xFapiInteractionId
    ) {
        ResponseEntity<InlineResponse200> responseEntity;

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        try {

            final List<by.openbanking.openbankingservice.models.Account> accountsForResponse =
                    AccountConverter.toAccount(mAccountRepository.findAll());

            if (!accountsForResponse.isEmpty()) {
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

                responseEntity =  new ResponseEntity<>(respData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
