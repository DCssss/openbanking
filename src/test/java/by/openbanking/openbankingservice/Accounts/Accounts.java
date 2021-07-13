package by.openbanking.openbankingservice.Accounts;

import by.openbanking.openbankingservice.models.InlineResponse200;
import by.openbanking.openbankingservice.models.InlineResponse2001;
import by.openbanking.openbankingservice.repository.AccountsRepository;
import by.openbanking.openbankingservice.service.AccountsService;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class Accounts {
    private AccountsService accountsService;


    @Test
    public void testGetAccounts() throws SQLException {

        String xFapiAuthDate = "2021.07.10";
        String xFapiCustomerIpAddress = "192.168.1.1";
        String xFapiInteractionId = "2";
        String authorization = "sdfsdfs";

        accountsService.getAccounts();

    }

    @Test
    public void testGetAccountsById() {
        String accountId = "2";
        String xFapiAuthDate = "sdfsd";
        String xFapiCustomerIpAddress = "192.167.1.1";
        String xFapiInteractionId = "22dd";
        String authorization = "rterte2342";


      InlineResponse2001 inl =    accountsService.getAccountsById(accountId,xFapiAuthDate,xFapiCustomerIpAddress,xFapiInteractionId,authorization);

    }
}
