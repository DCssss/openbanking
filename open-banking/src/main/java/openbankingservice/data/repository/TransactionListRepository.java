package openbankingservice.data.repository;


import openbankingservice.data.entity.TransactionListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionListRepository extends JpaRepository<TransactionListEntity, Long> {

    @Query(value = "SELECT * FROM OB_LIST_TRANSACTIONS l WHERE l.ACCOUNT_ID = ?1 AND l.LIST_TRANSACTION_ID = ?2", nativeQuery = true)
    List<TransactionListEntity> findListTransactionsByAccountID(String accountID, String listTransactionID);

    @Query(value = "SELECT * FROM OB_LIST_TRANSACTIONS l WHERE l.ACCOUNT_ID = ?1", nativeQuery = true)
    List<TransactionListEntity> findListTransactionsById(String accountID);


}
