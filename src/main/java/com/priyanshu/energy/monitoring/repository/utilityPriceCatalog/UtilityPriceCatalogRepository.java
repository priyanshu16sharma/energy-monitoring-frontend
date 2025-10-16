package com.priyanshu.energy.monitoring.repository.utilityPriceCatalog;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priyanshu.energy.monitoring.entity.utilityPriceCatalog.UtilityPriceCatalogEntity;

public interface UtilityPriceCatalogRepository extends JpaRepository<UtilityPriceCatalogEntity, String> {
    List<UtilityPriceCatalogEntity> findByPincodeAndEffectiveFromBeforeOrderByEffectiveFromAsc(
            String pincode, LocalDate date);
}
