package com.example.school.repositories;

import com.example.school.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);  // Changed from findByUsername to findByEmail
    Boolean existsByEmail(String email);  // Changed from existsByUsername to existsByEmail
    
    
}
