package com.priyanshu.energy.monitoring.Service.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.dto.meter.MeterCreateDTO;
import com.priyanshu.energy.monitoring.dto.userdto.CustomerCreateDTO;
import com.priyanshu.energy.monitoring.dto.userdto.CustomerDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;
import com.priyanshu.energy.monitoring.entity.meter.MeterEntity;
import com.priyanshu.energy.monitoring.entity.user.CustomerEntity;
import com.priyanshu.energy.monitoring.entity.user.UserEntity;
import com.priyanshu.energy.monitoring.repository.meter.MeterRepository;
import com.priyanshu.energy.monitoring.repository.user.CustomerRepository;
import com.priyanshu.energy.monitoring.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {
        private final UserRepository userRepository;
        private final CustomerRepository customerRepository;
        private final MeterRepository meterRepository;
        private final PasswordEncoder passwordEncoder;

        public CustomerService(UserRepository userRepository, CustomerRepository customerRepository,
                        MeterRepository meterRepository) {
                this.userRepository = userRepository;
                this.customerRepository = customerRepository;
                this.meterRepository = meterRepository;
                this.passwordEncoder = new BCryptPasswordEncoder();
        }

        public List<CustomerDTO> getAllCustomers() {
                return customerRepository.findAll()
                                .stream()
                                .map(this::mapToDTO)
                                .toList();
        }

        public CustomerCreateDTO getCustomerById(String id) {
                CustomerEntity customer = customerRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Customer not found  id: " + id));

                return mapToDTOWithMeter(customer);
        }

        public void deleteCustomer(String customerId) {
                CustomerEntity customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> new IllegalArgumentException("No Such User Exist"));

                userRepository.deleteById(customer.getUser().getId());
        }

        @Transactional
        public CustomerCreateDTO createCustomerUser(CustomerCreateDTO customerData) {

                if (userRepository.findByEmail(customerData.getUser().getEmail()).isPresent()) {
                        throw new IllegalArgumentException(
                                        "User with email " + customerData.getUser().getEmail() + " already exists.");
                }

                if (customerRepository.findByPhoneNumber(customerData.getPhoneNumber()).isPresent()) {
                        throw new IllegalArgumentException(
                                        "User with Phone Number " + customerData.getPhoneNumber() + " already exists.");
                }

                if (!"customer".equalsIgnoreCase(customerData.getUser().getRole().strip())) {
                        throw new IllegalArgumentException("Invalid UserRole");
                }

                UserEntity user = new UserEntity();
                user.setFirstName(customerData.getUser().getFirstName());
                user.setLastName(customerData.getUser().getLastName());
                user.setEmail(customerData.getUser().getEmail());
                user.setRole("customer");
                user.setCreatedAt(LocalDateTime.now());
                user.setPassword(passwordEncoder.encode(customerData.getUser().getPassword()));

                CustomerEntity customer = new CustomerEntity();
                customer.setPhoneNumber(customerData.getPhoneNumber());
                customer.setPincode(customerData.getPincode());
                customer.setCity(customerData.getCity());
                customer.setState(customerData.getState());
                customer.setAddress(customerData.getAddress());

                MeterEntity meter = new MeterEntity();
                meter.setUsageType(customerData.getMeter().getUsageType());
                meter.setActive(customerData.getMeter().isActive());
                meter.setCreatedAt(LocalDateTime.now());

                user.setCustomer(customer);
                customer.setUser(user);
                customer.setMeter(meter);
                meter.setCustomer(customer);

                UserEntity savedUser = userRepository.save(user);

                CustomerCreateDTO response = new CustomerCreateDTO();
                CustomerEntity savedCustomer = savedUser.getCustomer();

                response.setId(savedCustomer.getId());
                response.setPhoneNumber(savedCustomer.getPhoneNumber());
                response.setPincode(savedCustomer.getPincode());
                response.setCity(savedCustomer.getCity());
                response.setState(savedCustomer.getState());
                response.setAddress(savedCustomer.getAddress());

                MeterEntity savedMeter = savedCustomer.getMeter();
                response.setMeter(new MeterCreateDTO(
                                savedMeter.getId(),
                                savedMeter.getUsageType(),
                                savedMeter.isActive(),
                                savedMeter.getCreatedAt()));

                UserEntity savedUserEntity = savedCustomer.getUser();
                response.setUser(new UserDTO(
                                savedUserEntity.getId(),
                                savedUserEntity.getFirstName(),
                                savedUserEntity.getLastName(),
                                savedUserEntity.getEmail(),
                                null, // password not exposed
                                savedUserEntity.getRole(),
                                savedUserEntity.getCreatedAt()));

                return response;
        }

        private CustomerCreateDTO mapToDTOWithMeter(CustomerEntity customer) {
                return new CustomerCreateDTO(
                                customer.getId(),
                                customer.getPhoneNumber(),
                                customer.getPincode(),
                                customer.getAddress(),
                                customer.getCity(),
                                customer.getState(),
                                new UserDTO(
                                                customer.getUser().getId(),
                                                customer.getUser().getFirstName(),
                                                customer.getUser().getLastName(),
                                                customer.getUser().getEmail(),
                                                null,
                                                customer.getUser().getRole(),
                                                customer.getUser().getCreatedAt()),
                                new MeterCreateDTO(
                                                customer.getMeter().getId(),
                                                customer.getMeter().getUsageType(),
                                                customer.getMeter().isActive(),
                                                customer.getMeter().getCreatedAt()));

        }

        public CustomerDTO mapToDTO(CustomerEntity customer) {
                return new CustomerDTO(
                                customer.getId(),
                                customer.getPhoneNumber(),
                                customer.getPincode(),
                                customer.getAddress(),
                                customer.getCity(),
                                customer.getState(),
                                new UserDTO(
                                                customer.getUser().getId(),
                                                customer.getUser().getFirstName(),
                                                customer.getUser().getLastName(),
                                                customer.getUser().getEmail(),
                                                null,
                                                customer.getUser().getRole(),
                                                customer.getUser().getCreatedAt()));

        }

}
