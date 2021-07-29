package by.openbanking.openbankingservice.data.repository;


import by.openbanking.openbankingservice.data.entity.TransactionListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionListRepository extends JpaRepository<TransactionListEntity, Long> {

    @Query(value = "SELECT * FROM OB_LIST_TRANSACTIONS l WHERE l.ACCOUNT_ID = ?1 AND l.LIST_TRANSACTION_ID = ?2", nativeQuery = true)
    List<TransactionListEntity> findListTransactionsByAccountID(String accountID, String listTransactionID);

    @Query(value = "SELECT * FROM OB_LIST_TRANSACTIONS l WHERE l.ACCOUNT_ID = ?1", nativeQuery = true)
    List<TransactionListEntity> findListTransactionsById(String accountID);


}
