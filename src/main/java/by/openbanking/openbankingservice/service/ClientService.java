package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.data.entity.ClientEntity;
import by.openbanking.openbankingservice.exception.OBErrorCode;
import by.openbanking.openbankingservice.exception.OBException;
import by.openbanking.openbankingservice.data.repository.ClientRepository;
import by.openbanking.openbankingservice.util.StubData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository mClientRepository;

    public ClientEntity identifyClient(
            final String apiKey
    ) {
        final Long clientId = StubData.CLIENTS.get(apiKey);
        if (clientId != null) {
            return mClientRepository.getById(clientId);
        } else {
            throw new OBException(OBErrorCode.BY_NBRB_HEADER_INVALID, "Illegal ApiKey");
        }
    }
}
