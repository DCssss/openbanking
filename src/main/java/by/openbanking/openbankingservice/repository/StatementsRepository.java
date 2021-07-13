package by.openbanking.openbankingservice.repository;


import by.openbanking.openbankingservice.model.Statements;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatementsRepository extends JpaRepository<Statements, Long> {

}