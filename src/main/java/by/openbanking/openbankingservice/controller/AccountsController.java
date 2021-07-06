package by.openbanking.openbankingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<List<Accounts>> getAccounts() {
        try {
            List<Accounts> acc = new ArrayList<Accounts>();
            accountsRepository.findAll().forEach(acc::add);

            if (acc.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(acc, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Accounts> getAccByAccountId(@PathVariable("accountId") long id) {
       Optional<Accounts> accData = accountsRepository.findById(id);

        if (accData.isPresent()) {
            return new ResponseEntity<>(accData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}