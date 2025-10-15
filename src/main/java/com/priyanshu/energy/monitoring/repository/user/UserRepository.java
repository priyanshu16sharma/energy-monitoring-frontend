package com.priyanshu.energy.monitoring.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.priyanshu.energy.monitoring.entity.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
