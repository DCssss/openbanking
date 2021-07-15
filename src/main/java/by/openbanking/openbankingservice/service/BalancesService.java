package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.model.Account;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.util.RightsController;
import by.openbanking.openbankingservice.util.StubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BalancesService {

    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    private final AccountConsentsRepository mAccountConsentsRepository;
    private final AccountRepository mAccountRepository;

    @Autowired
    public BalancesService(
            final AccountConsentsRepository accountConsentsRepository,
            final AccountRepository accountRepository
    ) {
        mAccountConsentsRepository = accountConsentsRepository;
        mAccountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<OBReadBalance1> getBalances(
            final String xFapiInteractionId,
            final String apikey
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);

        ResponseEntity<OBReadBalance1> responseEntity;

        //получить ClientId по apikey
        final Long clientId = StubData.CLIENTS.get(apikey);

        if (clientId != null && RightsController.isHaveRights(mAccountConsentsRepository, clientId, "/balances")) {

            Date now = new Date();
            final List<Account> accounts = mAccountRepository.findAll();
            if (!accounts.isEmpty()) {
                final List<OBReadBalance1DataBalance> balances = new ArrayList<>();

                for (Account account : accounts) {
                    final OBReadBalance1DataBalance balance = new OBReadBalance1DataBalance();
                    balance.setAccountId(String.valueOf(account.getAccountId()));
                    balance.setDateTime(now);
                    balance.setCurrency(account.getAccountCurrency());
                    balance.setBalanceAmount(account.getAccountBalanceAmount().toString());

                    balances.add(balance);
                }

                final OBReadBalance1Data readBalance1Data = new OBReadBalance1Data();
                readBalance1Data.setBalance(balances);

                final LinksBalance links = new LinksBalance();
                links.setSelf("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

                final Meta meta = new Meta();
                meta.setTotalPages(1);
                meta.setFirstAvailableDateTime(now);
                meta.setLastAvailableDateTime(now);

                final OBReadBalance1 respData = new OBReadBalance1();
                respData.setData(readBalance1Data);
                respData.setLinks(links);
                respData.setMeta(meta);

                responseEntity = new ResponseEntity<>(respData, headers, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } else {
            responseEntity = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }
}
