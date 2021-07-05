package by.openbanking.openbankingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import by.openbanking.openbankingservice.model.AccountConsents;
import org.eclipse.wst.xml.xpath2.api.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.openbanking.openbankingservice.model.Accounts;
import by.openbanking.openbankingservice.repository.AccountsRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


@RestController
@RequestMapping("")
public class AccountsController {
///test2
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
    public ResponseEntity<Accounts> getAccByAccountId(@PathVariable("accountId") long accountId) {
       Optional<Accounts> accData = accountsRepository.findById(accountId);

        List<Long> acc = accountsRepository.findAll()
                .stream()
                .map(i-> i.getAccountId())
                .collect(Collectors.toList());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("He");
        EntityManager em = emf.createEntityManager();

        if (accData.isPresent()) {
            return new ResponseEntity<>(accData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}