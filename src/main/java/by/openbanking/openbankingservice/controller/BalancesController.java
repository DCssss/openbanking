package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.api.BalancesApi;
import by.openbanking.openbankingservice.model.Accounts;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BalancesController implements BalancesApi {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    private final AccountsRepository accountsRepository;

    @Autowired
    public BalancesController(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public ResponseEntity<OBReadBalance1> getBalances(
           final String xFapiAuthDate,
           final String xFapiCustomerIpAddress,
           final String xFapiInteractionId,
           final String authorization
    ) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

            List<Accounts> accountFromRepository = new ArrayList<>(accountsRepository.findAll());
            List<Account> accountForResponse = new ArrayList<>();
            for (Accounts ac : accountFromRepository){
                accountForResponse.add(ac.toAccount());
            }

            if (accountForResponse.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            by.openbanking.openbankingservice.models.Accounts accData = new by.openbanking.openbankingservice.models.Accounts();
            accData.setAccounts(accountForResponse);

            OBReadBalance1 respData = new OBReadBalance1();
            OBReadBalance1Data readBalance1Data = new OBReadBalance1Data();
            OBReadBalance1DataBalance readBalance1DataBalance = new OBReadBalance1DataBalance();
            List<OBReadBalance1DataBalance> listBalance = new ArrayList<>();

            listBalance.add(readBalance1DataBalance);
            readBalance1Data.setBalance(listBalance);

            //Надо не забыть доделать блоки Link и Meta , пока заглушки
            Date now = new Date();
            LinksBalance links = new LinksBalance();
            Accounts.Meta meta = new Accounts.Meta();
            links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");
            meta.setTotalPages(1);
            meta.setFirstAvailableDateTime(now);
            meta.setLastAvailableDateTime(now);

            respData.setData(readBalance1Data);
            respData.setLinks(links);
            respData.setMeta(meta);

            return  new ResponseEntity<>(respData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
