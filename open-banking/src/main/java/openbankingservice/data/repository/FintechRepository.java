package openbankingservice.data.repository;

import openbankingservice.data.entity.FintechEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FintechRepository extends JpaRepository<FintechEntity, Long> {
}
