package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.Account;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.ConsentRepository;
import by.openbanking.openbankingservice.util.RightsController;
import by.openbanking.openbankingservice.util.StubData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalancesService {

    private static final String X_FAPI_AUTH_DATE = "x-fapi-auth-date";
    private static final String X_FAPI_CUSTOMER_IP_ADDRESS = "x-fapi-customer-ip-address";
    private static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    private static final String AUTHORIZATION = "authorization";
    private static final String X_API_KEY = "x-api-key";
    private static final String X_ACCOUNT_CONSENT_ID = "x-accountConsentId";

    private final ConsentRepository mConsentRepository;
    private final AccountRepository mAccountRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<OBReadBalance1> getBalances(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApikey,
            final String xAccountConsentId
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(X_FAPI_INTERACTION_ID, xFapiInteractionId);
        headers.add(X_API_KEY, xApikey);


        ResponseEntity<OBReadBalance1> responseEntity;

        //получить ClientId по apikey
        final Long clientId = StubData.CLIENTS.get(xApikey);

        if (clientId != null && RightsController.isHaveRights(mConsentRepository, clientId, "/balances")) {

            Date now = new Date();
            final List<Account> accounts = mAccountRepository.findAll();
            if (!accounts.isEmpty()) {
                final List<OBReadBalance1DataBalance> balances = new ArrayList<>();

                for (Account account : accounts) {
                    final OBReadBalance1DataBalance balance = new OBReadBalance1DataBalance();
                    balance.setAccountId(String.valueOf(account.getId()));
                    balance.setDateTime(now);
                    balance.setCurrency(account.getCurrency());
                    balance.setBalanceAmount(account.getBalanceAmount().toString());

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
                responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } else {
            responseEntity = new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return responseEntity;
    }
}
