package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.entity.AccountEntity;
import by.openbanking.openbankingservice.entity.ConsentEntity;
import by.openbanking.openbankingservice.models.*;
import by.openbanking.openbankingservice.repository.AccountRepository;
import by.openbanking.openbankingservice.repository.ConsentRepository;
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

    private final ClientService mClientService;
    private final ConsentService mConsentService;

    @Transactional(readOnly = true)
    public ResponseEntity<BalanceResponse> getBalances(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApikey,
            final String xAccountConsentId
    ) {
        mClientService.identifyClient(xApikey);
        final ConsentEntity consent = mConsentService.checkPermissionAndGetConsent(Long.valueOf(xAccountConsentId), "/balances");

        final Date now = new Date();
        final List<Balance> balances = new ArrayList<>();

        for (AccountEntity account : consent.getAccounts()) {
            final Balance balance = new Balance();
            balance.setAccountId(String.valueOf(account.getId()));
            balance.setDateTime(now);
            balance.setCurrency(account.getCurrency());
            balance.setBalanceAmount(account.getBalanceAmount().toString());

            balances.add(balance);
        }

        final BalanceResponseData balanceResponseData = new BalanceResponseData();
        balanceResponseData.setBalance(balances);

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

    }
}
