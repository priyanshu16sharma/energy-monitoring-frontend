package com.priyanshu.energy.monitoring.controller.billing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyanshu.energy.monitoring.Service.billing.BillingService;
import com.priyanshu.energy.monitoring.dto.billing.BillingDTO;
import com.priyanshu.energy.monitoring.dto.billing.BillingGenerateDTO;
import com.priyanshu.energy.monitoring.dto.billing.BillingReportDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBilling(@Valid @RequestBody BillingGenerateDTO billData) {
        try {
            BillingDTO bill = billingService.generateBill(billData.getCustomerId(), billData.getCalcFrom(),
                    billData.getCalcTo(), billData.getTotalUnits());
            return ResponseEntity.status(HttpStatus.OK).body(bill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getBills() {

        try {
            List<BillingDTO> bills = billingService.getBills();

            return ResponseEntity.status(HttpStatus.OK).body(bills);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/get-individual-bill/{id}")
    public ResponseEntity<?> getBill(@PathVariable String id) {
        try {
            BillingReportDTO bill = billingService.getBillsById(id);
            return ResponseEntity.status(HttpStatus.OK).body(bill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
