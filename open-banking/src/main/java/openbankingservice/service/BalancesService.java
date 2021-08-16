package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.AccountEntity;
import openbankingservice.data.entity.ConsentEntity;
import openbankingservice.models.accinfo.*;
import openbankingservice.util.OBHttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static openbankingservice.util.OBHttpHeaders.X_ACCOUNT_CONSENT_ID;

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
        final List<BalanceCreditLine> creditLine = new ArrayList<>();
        for (AccountEntity account : consent.getAccounts()) {
            final Balance balance = new Balance()
                    .accountId(String.valueOf(account.getId()))
                    .creditDebitIndicator(account.getBalanceAmount().compareTo(BigDecimal.ZERO) > 0 ? OBCreditDebitCode2.CREDIT : OBCreditDebitCode2.DEBIT)
                    .type(BalanceType.OPENINGAVAILABLE)
                    .dateTime(now)
                    .currency(account.getCurrency())
                    .balanceAmount(account.getBalanceAmount().toString())
                    .balanceEquivalentAmount(account.getBalanceAmount().toString())
                    .creditLine(creditLine);
            balances.add(balance);
        }

        final BalanceResponseData balanceResponseData = new BalanceResponseData();
        balanceResponseData.setBalance(balances);

        final LinksBalance links = new LinksBalance()
                .self("https://paymentapi.st.by:8243/open-banking/v1.0/balances");

        final Meta meta = new Meta()
                .totalPages(1)
                .firstAvailableDateTime(now)
                .lastAvailableDateTime(now);

        final BalanceResponse respData = new BalanceResponse()
                .data(balanceResponseData)
                .links(links)
                .meta(meta);

        final HttpHeaders headers = new OBHttpHeaders().getAccInfoHeaders (
                xFapiInteractionId,
                authorization,
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xApikey
        );
        headers.add(OBHttpHeaders.X_ACCOUNT_CONSENT_ID,xAccountConsentId);

        return new ResponseEntity<>(respData, headers, HttpStatus.OK);

    }
}
