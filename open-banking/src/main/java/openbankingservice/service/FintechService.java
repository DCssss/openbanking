package openbankingservice.service;

import openbankingservice.data.entity.FintechEntity;
import openbankingservice.data.repository.FintechRepository;
import openbankingservice.exception.OBErrorCode;
import openbankingservice.exception.OBException;
import openbankingservice.util.StubData;
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