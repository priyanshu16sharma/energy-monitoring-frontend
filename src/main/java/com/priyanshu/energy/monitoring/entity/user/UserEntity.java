package com.priyanshu.energy.monitoring.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
@ToString(exclude = "admin")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, length = 255)
    @Size(max = 255, message = "Password cannot exceed 255 characters.")
    private String password;

    @NotNull
    @Pattern(regexp = "^(admin|superAdmin|customer)$", message = "Role must be one of admin, superAdmin, or customer")
    @Column(nullable = false, length = 12)
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerEntity customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private AdminEntity admin;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

}