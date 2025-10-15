package com.priyanshu.energy.monitoring.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priyanshu.energy.monitoring.entity.user.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}
