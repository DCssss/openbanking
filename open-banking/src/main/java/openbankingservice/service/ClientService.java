package openbankingservice.service;

import lombok.RequiredArgsConstructor;
import openbankingservice.data.entity.ClientEntity;
import openbankingservice.data.repository.ClientRepository;
import openbankingservice.exception.OBErrorCode;
import openbankingservice.exception.OBException;
import openbankingservice.util.StubData;
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
