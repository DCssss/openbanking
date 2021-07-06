package by.openbanking.openbankingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import by.openbanking.openbankingservice.model.AccountConsentsInputModel;
import by.openbanking.openbankingservice.models.OBReadConsent1;
import by.openbanking.openbankingservice.models.OBReadConsent1Data;
import by.openbanking.openbankingservice.models.OBReadConsentResponse1Post;
import by.openbanking.openbankingservice.repository.AccountConsentsApi;
import by.openbanking.openbankingservice.repository.AccountConsentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.openbanking.openbankingservice.model.AccountConsents;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class AccountConsentController implements AccountConsentsApi {

    @Autowired
    AccountConsentsRepository  accountConsentsRepository;

    @Override
    public ResponseEntity<AccountConsents> getAccConsentById(@PathVariable("accountConsentId") long accountConsentId) {
        Optional<AccountConsents> accountConsentsData = accountConsentsRepository.findById(accountConsentId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("header1","Test232222qw234dsfgd");
        if (accountConsentsData.isPresent()) {
            return new ResponseEntity<>(accountConsentsData.get(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //вручную написанный контроллер
    @Override
    public ResponseEntity<List<AccountConsents>> getAllAccConsents(@RequestParam(required = false) String accountConsentId) {
        try {
            List<AccountConsents> accConsents = new ArrayList<AccountConsents>();

            if (accountConsentId == null)
                accountConsentsRepository.findAll().forEach(accConsents::add);
            else
                accountConsentsRepository.findByAccountConsentIdContaining(accountConsentId).forEach(accConsents::add);

            if (accConsents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accConsents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //вручную написанный контроллер
    @Override
    public ResponseEntity<AccountConsents> createAccConsents(@RequestBody AccountConsentsInputModel accConsents) {
        try {
            AccountConsents accountConsents = AccountConsents.valueOf(accConsents);
            accountConsentsRepository.save(accountConsents);


            HttpHeaders header = new HttpHeaders();
            header.add("header1",accountConsents.getAccountConsentId());

            return new ResponseEntity<>(accountConsents, header, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //вручную написанный контроллер
    @Override
    public ResponseEntity<HttpStatus> deleteAccConsent(@PathVariable("accountConsentId") long id) {
        try {
            accountConsentsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //обработка автосгенерированной модели codegen из swagger
    public ResponseEntity<OBReadConsentResponse1Post> createAccountAccessConsents(@RequestBody OBReadConsent1 body) {
        try {
            OBReadConsent1Data accConsData = new OBReadConsent1Data();
           /* OBReadConsent1 _accConsents = accountConsentsRepository
                    .save(new OBReadConsent1(accConsData.permissions()));
            return new ResponseEntity<>(_accConsents, HttpStatus.CREATED); */
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
