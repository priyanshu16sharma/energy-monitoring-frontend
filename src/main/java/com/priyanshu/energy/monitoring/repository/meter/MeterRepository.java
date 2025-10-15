package com.priyanshu.energy.monitoring.repository.meter;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priyanshu.energy.monitoring.entity.meter.MeterEntity;

public interface MeterRepository extends JpaRepository<MeterEntity, String> {

}