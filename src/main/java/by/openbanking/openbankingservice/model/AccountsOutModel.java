package by.openbanking.openbankingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AccountsOutModel {

    private Data Data;


    public AccountsOutModel() {
    }

    @JsonProperty("Data")
    public AccountsOutModel.Data getData() {
        return Data;
    }

    public void setData(AccountsOutModel.Data Data) {
        this.Data = Data;
    }

    public static final class Data{

        List<Accounts> accounts = new ArrayList<>();

        @JsonProperty("Accounts")
        public List<Accounts> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<Accounts> accounts) {
            this.accounts = accounts;
        }
    }
}
