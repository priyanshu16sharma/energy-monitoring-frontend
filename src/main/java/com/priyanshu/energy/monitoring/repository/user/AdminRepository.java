package com.priyanshu.energy.monitoring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priyanshu.energy.monitoring.entity.user.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, String> {

}