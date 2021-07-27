package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByBookingTimeBetween(final Date bookingTimeFrom, final Date bookingTimeTo);
}
