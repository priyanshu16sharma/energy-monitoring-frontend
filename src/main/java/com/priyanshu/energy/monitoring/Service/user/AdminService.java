package com.priyanshu.energy.monitoring.Service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.priyanshu.energy.monitoring.dto.userdto.AdminDTO;
import com.priyanshu.energy.monitoring.dto.userdto.UserDTO;
import com.priyanshu.energy.monitoring.entity.user.AdminEntity;
import com.priyanshu.energy.monitoring.entity.user.UserEntity;
import com.priyanshu.energy.monitoring.repository.user.AdminRepository;
import com.priyanshu.energy.monitoring.repository.user.CustomerRepository;
import com.priyanshu.energy.monitoring.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository,
            AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void deleteAdmin(String adminId) {
        AdminEntity admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("No Such User Exist"));

        userRepository.deleteById(admin.getUser().getId());
    }

    public AdminDTO getAdminById(String id) {
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));

        return mapToDTO(admin);
    }

    @Transactional
    public AdminDTO createAdminUser(AdminDTO adminDTO) throws IllegalArgumentException {

        if (userRepository.findByEmail(adminDTO.getUser().getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + adminDTO.getUser().getEmail() + " already exists.");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(adminDTO.getUser().getFirstName());
        user.setLastName(adminDTO.getUser().getLastName());
        user.setEmail(adminDTO.getUser().getEmail());
        user.setRole(adminDTO.getUser().getRole());
        user.setCreatedAt(LocalDateTime.now());

        String rawPassword = adminDTO.getUser().getPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        adminDTO.getUser().setPassword(encryptedPassword);

        log.info(encryptedPassword);
        user.setPassword(encryptedPassword);

        AdminEntity admin = new AdminEntity();
        admin.setAssignedPincode(adminDTO.getAssignedPincode());
        admin.setUser(user);

        user.setAdmin(admin);
        UserEntity savedUser = userRepository.save(user);
        System.out.println(savedUser);

        AdminDTO response = new AdminDTO();
        response.setId(savedUser.getAdmin().getId());
        response.setAssignedPincode(savedUser.getAdmin().getAssignedPincode());
        response.setUser(new UserDTO(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                null, // don't expose password
                savedUser.getRole(),
                savedUser.getCreatedAt()));
        return response;

    }

    private AdminDTO mapToDTO(AdminEntity admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getAssignedPincode(),
                new UserDTO(
                        admin.getUser().getId(),
                        admin.getUser().getFirstName(),
                        admin.getUser().getLastName(),
                        admin.getUser().getEmail(),
                        null,
                        admin.getUser().getRole(),
                        admin.getUser().getCreatedAt()));
    }

}
