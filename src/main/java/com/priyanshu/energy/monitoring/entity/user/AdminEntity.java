package com.priyanshu.energy.monitoring.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "admins")
@ToString(exclude = "user")
public class AdminEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @NotNull
    @Column(name = "assigned_pincode", nullable = false, length = 6)
    @Size(min = 6, max = 6, message = "Pincode must be 6 characters long")
    private String assignedPincode;

}