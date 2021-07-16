package by.openbanking.openbankingservice.controllers;

import by.openbanking.openbankingservice.api.BalancesApi;
import by.openbanking.openbankingservice.models.OBReadBalance1;
import by.openbanking.openbankingservice.service.BalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class BalancesController implements BalancesApi {

    private final BalancesService mBalancesService;

    @Autowired
    public BalancesController(
            final BalancesService balancesService
    ) {
        mBalancesService = balancesService;
    }

    @Override
    public ResponseEntity<OBReadBalance1> getBalances(
            final String xFapiAuthDate,
            final String xFapiCustomerIpAddress,
            final String xFapiInteractionId,
            final String authorization,
            final String xApikey,
            final String xAccountConsentId
    ) {
        return mBalancesService.getBalances(xFapiAuthDate,xFapiCustomerIpAddress,xFapiInteractionId,authorization, xApikey, xAccountConsentId);
    }
}
