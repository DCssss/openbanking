package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.data.entity.AccountEntity;
import by.openbanking.openbankingservice.data.entity.ConsentEntity;
import by.openbanking.openbankingservice.models.accinfo.*;
import by.openbanking.openbankingservice.util.OBHttpHeaders;
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

        final LinksBalance links = new LinksBalance()
                .self("https://api.bank.by/oapi-channel/open-banking/v1.0/accounts/");

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final BalanceResponse respData = new BalanceResponse()
                .data(balanceResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(OBHttpHeaders.X_FAPI_INTERACTION_ID, xFapiInteractionId);

        return new ResponseEntity<>(respData, headers, HttpStatus.OK);

    }
}
