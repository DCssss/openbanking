package by.openbanking.openbankingservice.repository;


import by.openbanking.openbankingservice.model.Statement2Transaction;
import by.openbanking.openbankingservice.model.Statements;
import by.openbanking.openbankingservice.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface Statement2TransactionRepository extends JpaRepository<Statement2Transaction, Long> {

   /* @Query(value = "SELECT * FROM  OB_STATEMENT_2_TRANSACTION l INNER JOIN OB_TRANSACTIONS t ON l.TRANSACTION_ID= t.TRANSACTION_ID", nativeQuery = true)
    List<Transactions> findTransactionsForStatement(long transaction_id); */

}