package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.entity.Fintech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FintechRepository extends JpaRepository<Fintech, Long> {
}
