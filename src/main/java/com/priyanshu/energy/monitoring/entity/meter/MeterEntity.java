package com.priyanshu.energy.monitoring.entity.meter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.priyanshu.energy.monitoring.entity.billing.BillingEntity;
import com.priyanshu.energy.monitoring.entity.user.CustomerEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "meters")
public class MeterEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "usage_type", nullable = false)
    private String usageType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @NotNull
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "meter", fetch = FetchType.LAZY)
    private List<BillingEntity> bills;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

}
