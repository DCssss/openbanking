package openbankingservice.data.repository;

import openbankingservice.data.entity.FintechEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FintechRepository extends JpaRepository<FintechEntity, Long> {
}
