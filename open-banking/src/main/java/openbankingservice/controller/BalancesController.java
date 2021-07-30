package openbankingservice.controller;

import openbankingservice.api.accinfo.BalancesApi;
import openbankingservice.models.accinfo.BalanceResponse;
import openbankingservice.service.BalancesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalancesController implements BalancesApi {

    private final BalancesService mBalancesService;

    @Override
    public ResponseEntity<BalanceResponse> getBalances(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApikey,
            final String xAccountConsentId
    ) {
        return mBalancesService.getBalances(
                xFapiAuthDate,
                xFapiCustomerIpAddress,
                xFapiInteractionId,
                authorization,
                xApikey,
                xAccountConsentId
        );
    }
}
