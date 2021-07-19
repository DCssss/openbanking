package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.Fintech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FintechRepository extends JpaRepository<Fintech, Long> {
}
