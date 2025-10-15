package com.priyanshu.energy.monitoring.controller.admin;

import java.util.List;

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

import com.priyanshu.energy.monitoring.Service.user.AdminService;
import com.priyanshu.energy.monitoring.Service.user.UserService;
import com.priyanshu.energy.monitoring.dto.userdto.AdminDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminUserController {
    public final AdminService adminService;
    public final UserService userService;

    @Autowired
    public AdminUserController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAdmins() {
        try {
            List<AdminDTO> admins = adminService.getAllAdmins();
            return ResponseEntity.status(HttpStatus.OK).body(admins);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Something went wrong while fetching admins. \n" + e);
        }
    }

    @GetMapping("/get-individual-admin/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable String id) {
        try {
            AdminDTO admin = adminService.getAdminById(id);
            return ResponseEntity.status(HttpStatus.OK).body(admin);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Something went wrong while fetching admin. \n" + e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        try {
            AdminDTO createdAdmin = adminService.createAdminUser(adminDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Something went wrong while creating admin. \n" + e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Error: \n" + e);
        }
    }
}
