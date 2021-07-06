package by.openbanking.openbankingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import by.openbanking.openbankingservice.model.AccountsOutModel;
import by.openbanking.openbankingservice.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import by.openbanking.openbankingservice.model.Accounts;
import by.openbanking.openbankingservice.repository.AccountsRepository;

@RestController
@RequestMapping("")
public class AccountsController {

    @Autowired
    AccountsRepository accountsRepository;

    @GetMapping("/accounts")
    public ResponseEntity<AccountsOutModel> getAccounts() {
        try {
            AccountsOutModel outModel = new AccountsOutModel();
            List<Accounts> acc = new ArrayList<Accounts>();
            accountsRepository.findAll().forEach(acc::add);
            AccountsOutModel.Data data = new AccountsOutModel.Data();
            data.setAccounts(acc);
            outModel.setData(data);

            if (acc.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(outModel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountsOutModel> getAccByAccountId(@PathVariable("accountId") long id) {

        List<Accounts> acc = new ArrayList<Accounts>();
        Optional<Accounts> accData = accountsRepository.findById(id);
        acc.add(accData.get());
        AccountsOutModel outModel = new AccountsOutModel();
        AccountsOutModel.Data data = new AccountsOutModel.Data();
        data.setAccounts(acc);
        outModel.setData(data);

        if (accData.isPresent()) {
            return new ResponseEntity<>(outModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}