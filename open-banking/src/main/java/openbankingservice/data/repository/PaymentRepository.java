package openbankingservice.data.repository;

import openbankingservice.data.entity.PaymentEntity;
import openbankingservice.models.payments.TypePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p " +
            "FROM PaymentEntity p " +
            "WHERE p.createTime BETWEEN :fromCreationTime AND :toCreationTime " +
            "AND (:type IS NULL OR p.type = :type) " +
            "AND (:status IS NULL OR p.status = :status)")
    List<PaymentEntity> findAllByCreateTimeBetweenAndTypeAndStatus(
            final Date fromCreationTime,
            final Date toCreationTime,
            final TypePayment type,
            final PaymentEntity.Status status
    );

}
