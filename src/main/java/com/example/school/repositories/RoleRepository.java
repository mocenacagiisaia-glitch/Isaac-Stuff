package com.example.school.repositories;

import com.example.school.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

    Boolean existsByName(String name); // New method to check if a role exists by name
}
