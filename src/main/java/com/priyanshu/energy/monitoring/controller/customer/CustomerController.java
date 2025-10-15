package com.priyanshu.energy.monitoring.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyanshu.energy.monitoring.Service.user.CustomerService;
import com.priyanshu.energy.monitoring.Service.user.UserService;
import com.priyanshu.energy.monitoring.dto.userdto.CustomerCreateDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    public UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CustomerCreateDTO customerData) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomerUser(customerData));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("" + e);
        }

    }

    @GetMapping("")
    public ResponseEntity<?> getCustomers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Error: " + e);
        }
    }

    @GetMapping("/get-individual-customer/{id}")
    public ResponseEntity<?> getIndividualCustomer(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Error: " + e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Error: \n" + e);

        }
    }
}
