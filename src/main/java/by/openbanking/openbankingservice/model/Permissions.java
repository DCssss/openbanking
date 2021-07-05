package by.openbanking.openbankingservice.model;

import by.openbanking.openbankingservice.models.OBReadConsent1Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class Permissions {

    @JsonProperty("Permissions")
    @Valid
    private List<OBReadConsent1Data.PermissionsEnum> permissions = new ArrayList<OBReadConsent1Data.PermissionsEnum>();

    public List<OBReadConsent1Data.PermissionsEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<OBReadConsent1Data.PermissionsEnum> permissions) {
        this.permissions = permissions;
    }
}
