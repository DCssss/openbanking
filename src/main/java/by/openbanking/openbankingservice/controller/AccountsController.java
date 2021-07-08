package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.AccountsApi;
import by.openbanking.openbankingservice.model.*;
import by.openbanking.openbankingservice.models.InlineResponse200;
import by.openbanking.openbankingservice.models.InlineResponse2001;
import by.openbanking.openbankingservice.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
public class AccountsController implements AccountsApi {
    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsController(AccountsRepository repository) {
        this.accountsRepository = repository;
    }

    @Override
    public ResponseEntity<InlineResponse2001> accountsAccountIdGet(
            @Size(min = 1, max = 35) final String accountId,
            String xFapiAuthDate,
            String xFapiCustomerIpAddress,
            String xFapiInteractionId,
            String authorization) {

        ResponseEntity<InlineResponse2001> response;

        final Optional<Accounts> accountData = accountsRepository.findById(Long.valueOf(accountId));

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        if (accountData.isPresent()) {

            final InlineResponse2001 respData = new InlineResponse2001();
            by.openbanking.openbankingservice.models.Accounts accData = new by.openbanking.openbankingservice.models.Accounts();

            accData.setAccounts(accountData.get().toAccount());
            //Надо не забыть доделать блоки Link и Meta , пока заглушки
            Date now = new Date();
            Accounts.Links links = new Accounts.Links();
            Accounts.Meta meta = new Accounts.Meta();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            respData.setData(accData);
            respData.setLinks(links);
            respData.setMeta(meta);


            response = new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        return response;
    }

    @Override
    public ResponseEntity<InlineResponse200> getAccounts(
            String xFapiAuthDate,
            String xFapiCustomerIpAddress,
            String xFapiInteractionId,
            String authorization) {


        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

            List<Accounts> acc = new ArrayList<Accounts>();
            accountsRepository.findAll().forEach(acc::add);

            if (acc.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            by.openbanking.openbankingservice.models.Accounts accData = new by.openbanking.openbankingservice.models.Accounts();
            accData.setAccounts(acc.stream().findAny().get().toAccount());

            InlineResponse200 respData = new InlineResponse200();
            //Надо не забыть доделать блоки Link и Meta , пока заглушки
            Date now = new Date();
            Accounts.Link links = new Accounts.Link();
            Accounts.Meta meta = new Accounts.Meta();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            respData.setData(accData);
            respData.setLinks(links);
            respData.setMeta(meta);

            return  new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}