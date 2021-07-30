package openbankingservice.data.repository;


import openbankingservice.data.entity.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StatementRepository extends JpaRepository<StatementEntity, Long> {

    @Query(value = "SELECT * FROM OB_STATEMENTS l WHERE l.ACCOUNT_ID = ?1 AND l.STATEMENT_ID = ?2", nativeQuery = true)
    List<StatementEntity> findStatementsByAccountID(String accountID, String statementID);

    @Query(value = "SELECT * FROM OB_STATEMENTS l WHERE l.ACCOUNT_ID = ?1", nativeQuery = true)
    StatementEntity findStatementsById(String accountID);

  /*  @Query(value = "SELECT l.ACCOUNT_ID,l.STATEMENT_ID,t.TRANSACTION_ID FROM OB_STATEMENTS l INNER JOIN OB_TRANSACTIONS t ON l.ACCOUNT_ID = t.ACCOUNT_ID WHERE l.ACCOUNT_ID = ?1" , nativeQuery = true)
    List<Transactions> findTransactions(String accountID); */

}