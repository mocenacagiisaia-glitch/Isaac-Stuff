package com.example.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.school.models.Role;
import com.example.school.models.UserEntity;
import com.example.school.repositories.RoleRepository;
import com.example.school.repositories.UserRepository;

import java.util.Collections;

import jakarta.transaction.Transactional; // Ensure the transaction is managed

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional // Ensure everything runs in the same transaction
    public void run(String... args) throws Exception {
        // Create or retrieve roles
        Role adminRole = createOrRetrieveRole("ADMIN");
        Role studentRole = createOrRetrieveRole("STUDENT");
        Role parentRole = createOrRetrieveRole("PARENT");
        Role teacherRole = createOrRetrieveRole("TEACHER");

        // Create admin user if it doesn't exist
        if (!userRepository.existsByEmail("admin@xyz.com")) {
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@xyz.com");
            admin.setPassword(passwordEncoder.encode("admin"));

            // Assign the ADMIN role to the admin user
            admin.setRoles(Collections.singletonList(adminRole));
            userRepository.save(admin);
        }
        else if (!userRepository.existsByEmail("test@xyz.com")) {
    UserEntity test = new UserEntity();
    test.setEmail("test@xyz.com");
    test.setPassword(passwordEncoder.encode("1234"));
    test.setRoles(Collections.singletonList(studentRole));
    userRepository.save(test);
}
    }

    private Role createOrRetrieveRole(String roleName) {
        // Check if the role exists
        return roleRepository.findByName(roleName).orElseGet(() -> {
            // If the role does not exist, create and save a new one
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepository.save(newRole); // Save and return the new role
        });
    }
}
