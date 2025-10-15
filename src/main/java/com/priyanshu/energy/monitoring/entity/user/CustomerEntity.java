package com.priyanshu.energy.monitoring.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import com.priyanshu.energy.monitoring.entity.meter.MeterEntity;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeterEntity meter;

    @NotNull
    @Column(name = "phone_number", unique = true, nullable = false, length = 14)
    @Size(max = 14, message = "Phone number cannot be longer than 14 digits")
    private String phoneNumber;

    @NotNull
    @Column(nullable = false, length = 6)
    @Size(min = 6, max = 6, message = "Pincode must be 6 digits long")
    private String pincode;

    @NotNull
    @Column(nullable = false, length = 255)
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    @NotNull
    @Column(nullable = false, length = 56)
    @Size(max = 56, message = "City cannot exceed 56 characters")
    private String city;

    @NotNull
    @Column(nullable = false, length = 56)
    @Size(max = 56, message = "State cannot exceed 56 characters")
    private String state;

}