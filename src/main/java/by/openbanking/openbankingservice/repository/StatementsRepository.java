package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.model.Statements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementsRepository extends JpaRepository<Statements, Long> {

}