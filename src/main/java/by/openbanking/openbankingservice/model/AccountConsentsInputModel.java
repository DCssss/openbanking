package by.openbanking.openbankingservice.model;

import by.openbanking.openbankingservice.models.OBReadConsent1Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Date;

public class AccountConsentsInputModel {

    public Data Data;
    public static final class Data{
        public Collection<OBReadConsent1Data.PermissionsEnum> Permissions;
        public Date ExpirationDate;
        public Date TransactionFromDate;
        public Date TransactionToDate;
    }
}
