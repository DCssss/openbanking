package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.Account;
import by.openbanking.openbankingservice.model.AccountConsent2Account;
import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.models.AccountConsentsStatus;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.repository.AccountConsents2AccountRepository;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.util.StubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountConsentsService {

    private static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    private static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private static final String AUTHORIZATION = "authorization";
    private static final String X_API_KEY = "x-api-key";
    private static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";

    private final AccountConsentsRepository mAccountConsentsRepository;
    private final AccountRepository mAccountRepository;
    private final AccountConsents2AccountRepository mAccountConsents2AccountRepository;

    @Autowired
    public AccountConsentsService(
            final AccountConsentsRepository accountConsentsRepository,
            final AccountRepository accountRepository,
            final AccountConsents2AccountRepository accountConsents2AccountRepository
    ) {
        mAccountConsentsRepository = accountConsentsRepository;
        mAccountRepository = accountRepository;
        mAccountConsents2AccountRepository = accountConsents2AccountRepository;
    }

    @Transactional
    public ResponseEntity<Void> authorizeAccountConsents(
            final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        final Long clientId = StubData.CLIENTS.get(xApiKey);

        if (clientId != null) {

            final Optional<AccountConsents> accountConsentsOptional = mAccountConsentsRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {

                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setClientId(clientId);
                accountConsents.setStatus(AccountConsentsStatus.AUTHORISED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                mAccountConsentsRepository.save(accountConsents);

                final Collection<Account> clientAccounts = mAccountRepository.findByClientId(clientId);
                final Collection<AccountConsent2Account> accountConsent2Accounts = new ArrayList<>();
                for (Account account : clientAccounts) {
                    final AccountConsent2Account accountConsent2Account = new AccountConsent2Account();
                    accountConsent2Account.setAccountID(account.getAccountId());
                    accountConsent2Account.setAccountConsentID(Long.parseLong(accountConsentId));

                    accountConsent2Accounts.add(accountConsent2Account);
                }
                mAccountConsents2AccountRepository.saveAll(accountConsent2Accounts);

                response = new ResponseEntity<>(headers, HttpStatus.OK);

            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Transactional
    public ResponseEntity<Void> rejectAccountConsents(
            final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;

        //получить ClientId по apikey
        final Long clientId = StubData.CLIENTS.get(xApiKey);

        if (clientId != null) {

            //получить согласие
            final Optional<AccountConsents> accountConsentsOptional = mAccountConsentsRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {

                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setClientId(clientId);
                //отклонить согласие
                accountConsents.setStatus(AccountConsentsStatus.REJECTED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                //сохранить изменения
                mAccountConsentsRepository.save(accountConsents);
                response = new ResponseEntity<>(headers, HttpStatus.OK);

            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Transactional
    public ResponseEntity<OBReadConsentResponse1Post> createAccountConsents(
            @Valid final OBReadConsent1 body,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1Post> response;
        try {
            final AccountConsents accountConsents = AccountConsents.valueOf(body.getData());
            accountConsents.setStatus(AccountConsentsStatus.AWAITINGAUTHORISATION.toString());

            final Date now = new Date();
            accountConsents.setStatusUpdateTime(now);
            accountConsents.setCreationTime(now);
            mAccountConsentsRepository.save(accountConsents);

            final OBReadConsentResponse1Post responseContent = new OBReadConsentResponse1Post();
            responseContent.setData(accountConsents.toOBReadConsentResponsePost1Data());

            response = new ResponseEntity<>(responseContent, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<Void> deleteAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<Void> response;
        try {
            final Optional<AccountConsents> accountConsentsOptional = mAccountConsentsRepository.findById(Long.valueOf(accountConsentId));
            if (accountConsentsOptional.isPresent()) {
                final AccountConsents accountConsents = accountConsentsOptional.get();
                accountConsents.setStatus(AccountConsentsStatus.REVOKED.toString());
                accountConsents.setStatusUpdateTime(new Date());
                mAccountConsentsRepository.save(accountConsents);

                response = new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<OBReadConsentResponse1> getAccountAccessConsentsConsentId(
            @Size(min = 1, max = 35) final String accountConsentId,
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApiKey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadConsentResponse1> response;

        final Optional<AccountConsents> accountConsentsData = mAccountConsentsRepository.findById(Long.valueOf(accountConsentId));

        if (accountConsentsData.isPresent()) {
            final OBReadConsentResponse1 obReadConsentResponse1 = new OBReadConsentResponse1();
            obReadConsentResponse1.setData(accountConsentsData.get().toOBReadConsentResponse1Data());

            response = new ResponseEntity<>(obReadConsentResponse1, headers, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        return response;
    }
}
