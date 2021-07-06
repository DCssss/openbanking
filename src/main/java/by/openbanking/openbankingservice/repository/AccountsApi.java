package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.Accounts;
import by.openbanking.openbankingservice.model.AccountsOutModel;
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
    ResponseEntity<AccountsOutModel> getAccByAccountId(@PathVariable("accountId") long accountId);

    @RequestMapping(
            value = {"/accounts"},
            produces = {"application/json"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<AccountsOutModel> getAccounts(@RequestParam(required = false) String title);
}
