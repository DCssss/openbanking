package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.entity.FintechEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FintechRepository extends JpaRepository<FintechEntity, Long> {
}
