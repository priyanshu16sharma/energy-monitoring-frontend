package com.priyanshu.energy.monitoring.dto.billing;

import java.math.BigDecimal;

import com.priyanshu.energy.monitoring.dto.meter.MeterDTO;
import com.priyanshu.energy.monitoring.dto.userdto.CustomerDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingReportDTO {

    private BillingDTO bill;
    private BigDecimal pendingBill;
    private CustomerDTO customer;

}
