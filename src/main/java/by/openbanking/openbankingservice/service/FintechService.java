package by.openbanking.openbankingservice.service;

import by.openbanking.openbankingservice.data.entity.FintechEntity;
import by.openbanking.openbankingservice.exception.OBErrorCode;
import by.openbanking.openbankingservice.exception.OBException;
import by.openbanking.openbankingservice.data.repository.FintechRepository;
import by.openbanking.openbankingservice.util.StubData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FintechService {

    private final FintechRepository mFintechRepository;

    public FintechEntity identifyFintech(
            final String apiKey
    ) {
        final Long fintechId = StubData.FINTECHS.get(apiKey);
        if (fintechId != null) {
            return mFintechRepository.getById(fintechId);
        } else {
            throw new OBException(OBErrorCode.BY_NBRB_HEADER_INVALID, "Illegal ApiKey");
        }
    }
}