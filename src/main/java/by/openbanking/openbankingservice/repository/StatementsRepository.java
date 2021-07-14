package by.openbanking.openbankingservice.repository;


import by.openbanking.openbankingservice.model.ListTransactions;
import by.openbanking.openbankingservice.model.Statements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StatementsRepository extends JpaRepository<Statements, Long> {

    @Query(value = "SELECT * FROM OB_STATEMENTS l WHERE l.ACCOUNT_ID = ?1 AND l.STATEMENT_ID = ?2", nativeQuery = true)
    List<Statements> findStatementsByAccountID(String accountID, String statementID);

}