package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.Accounts;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class AccountsService {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public InlineResponse200 getAccounts() {

        try {
            List<Accounts> accountFromRepository = new ArrayList<>(accountsRepository.findAll());
            List<Account> accountForResponse = new ArrayList<>();
            for (Accounts ac : accountFromRepository){
                accountForResponse.add( ac.toAccount());
            }

            if (accountForResponse.isEmpty()) {
                return null;
            }

            by.openbanking.openbankingservice.models.Accounts accData = new by.openbanking.openbankingservice.models.Accounts();
            accData.setAccounts(accountForResponse);

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

            return  respData;
        } catch (Exception e) {
            return null;
        }
    }

    public InlineResponse2001 getAccountsById(
             String accountId,
            String xFapiAuthDate,
            String xFapiCustomerIpAddress,
            String xFapiInteractionId,
            String authorization) {

        InlineResponse2001 response;

        final Optional<Accounts> accountData = accountsRepository.findById(Long.valueOf(accountId));

        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        if (accountData.isPresent()) {

            final InlineResponse2001 respData = new InlineResponse2001();
            by.openbanking.openbankingservice.models.Accounts accData = new by.openbanking.openbankingservice.models.Accounts();
            List<Account> acc = new ArrayList<>();
            acc.add(accountData.get().toAccount());
            //acc.
            accData.setAccounts(acc);
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

            response = respData;

            return response ;
        } else {
           return null;
        }

    }

}
