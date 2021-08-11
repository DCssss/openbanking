package openbankingservice.data.repository;

import openbankingservice.data.entity.AccountEntity;
import openbankingservice.data.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByBookingTimeBetween(final Date bookingTimeFrom, final Date bookingTimeTo);
    List<TransactionEntity> findAllByAccountAndBookingTimeBetween(final AccountEntity account, final Date bookingTime, final Date bookingTime2);
}
