package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.Accounts;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(
        value = "accounts"
)
public interface AccountsApi {

    @RequestMapping(
            value = {"/accounts/{accountId}"},
            produces = {"application/json"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<Accounts> getAccByAccountId(@PathVariable("accountId") long accountId);

    @RequestMapping(
            value = {"/accounts"},
            produces = {"application/json"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<List<Accounts>> getAccounts(@RequestParam(required = false) String title);
}
