package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.AccountConsents;
import by.openbanking.openbankingservice.model.AccountConsentsInputModel;
import by.openbanking.openbankingservice.models.OBReadConsent1Data;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(
        value = "accountConsents"
)
public interface AccountConsentsApi {

    @RequestMapping(
            value = {"/accountConsents/{accountConsentId}"},
            produces = {"application/json"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<AccountConsents> getAccConsentById(@PathVariable("accountConsentId") long accountConsentId);

    @RequestMapping(
            value = {"/accountConsents"},
            produces = {"application/json"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<List<AccountConsents>> getAllAccConsents(@RequestParam(required = false) String title);

    @RequestMapping(
            value = {"/accountConsents"},
            produces = {"application/json"},
            method = {RequestMethod.POST}
    )
    ResponseEntity<AccountConsents> createAccConsents(@RequestBody AccountConsentsInputModel accConsents);

    @RequestMapping(
            value = {"/accountConsents/{accountConsentId}"},
            produces = {"application/json"},
            method = {RequestMethod.DELETE}
    )
    ResponseEntity<HttpStatus> deleteAccConsent(@PathVariable("accountConsentId") long accountConsentId);
}
