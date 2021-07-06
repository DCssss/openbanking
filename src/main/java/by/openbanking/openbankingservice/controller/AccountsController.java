package by.openbanking.openbankingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.openbanking.openbankingservice.model.AccountsOutModel;
import by.openbanking.openbankingservice.model.OBAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.openbanking.openbankingservice.repository.AccountsRepository;

@RestController
@RequestMapping("")
public class AccountsController {

    @Autowired
    AccountsRepository accountsRepository;

    @GetMapping("/accounts")
    public ResponseEntity<AccountsOutModel> getAccounts(
            @RequestHeader final Map<String, String> requestHeaders
    ) {

        try {

            AccountsOutModel outModel = new AccountsOutModel();
            List<OBAccounts> acc = new ArrayList<OBAccounts>();
            accountsRepository.findAll().forEach(acc::add);
            AccountsOutModel.Data data = new AccountsOutModel.Data();
            data.setAccounts(acc);
            outModel.setData(data);

            if (acc.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            final HttpHeaders headers = new HttpHeaders();
            headers.add("x-fapi-auth-date",requestHeaders.get("x-fapi-auth-date"));
            headers.add("x-fapi-customer-ip-address",requestHeaders.get("x-fapi-customer-ip-address"));
            headers.add("x-fapi-interaction-id",requestHeaders.get("x-fapi-interaction-id"));
            headers.add("authorization",requestHeaders.get("authorization"));

            return new ResponseEntity<>(outModel, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountsOutModel> getAccByAccountId(
            @RequestHeader final Map<String, String> requestHeaders,
            @PathVariable("accountId") long id
    ) {

        List<OBAccounts> acc = new ArrayList<OBAccounts>();
        Optional<OBAccounts> accData = accountsRepository.findById(id);
        acc.add(accData.get());
        AccountsOutModel outModel = new AccountsOutModel();
        AccountsOutModel.Data data = new AccountsOutModel.Data();
        data.setAccounts(acc);
        outModel.setData(data);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("x-fapi-auth-date",requestHeaders.get("x-fapi-auth-date"));
        headers.add("x-fapi-customer-ip-address",requestHeaders.get("x-fapi-customer-ip-address"));
        headers.add("x-fapi-interaction-id",requestHeaders.get("x-fapi-interaction-id"));
        headers.add("authorization",requestHeaders.get("authorization"));

        if (accData.isPresent()) {
            return new ResponseEntity<>(outModel, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}