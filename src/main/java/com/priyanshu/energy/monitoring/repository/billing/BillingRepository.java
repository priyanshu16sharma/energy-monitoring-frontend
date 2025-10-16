package com.priyanshu.energy.monitoring.repository.billing;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.priyanshu.energy.monitoring.entity.billing.BillingEntity;

@Repository
public interface BillingRepository extends JpaRepository<BillingEntity, String> {

    List<BillingEntity> findByStatus(String status);

    List<BillingEntity> findAllByMeterIdOrderByCreatedAtDesc(String meterId);

    List<BillingEntity> findAllByOrderByCreatedAtDesc();

    BillingEntity findTopByMeterIdOrderByCreatedAtDesc(String meterId);

    @Query("""
            SELECT COALESCE(SUM(b.totalAmount), 0)
            FROM BillingEntity b
            WHERE b.meter.id = :meterId
              AND LOWER(b.status) = LOWER(:status)
            """)
    BigDecimal findTotalAmountByMeterIdAndStatus(@Param("meterId") String meterId, @Param("status") String status);

}
