package by.openbanking.openbankingservice.data.repository;

import by.openbanking.openbankingservice.data.entity.FintechEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FintechRepository extends JpaRepository<FintechEntity, Long> {
}
