package com.example.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.school.models.Teacher;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    
    // Check for duplicate emails
    boolean existsByEmail(String email);
    
    
 // Method to find a teacher by email
    Teacher findByEmail(String email); // Fetch
    
    // Delete a teacher by email
    void deleteByEmail(String email);
    
    List<Teacher> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Teacher t WHERE t.id = :userId")
    void deleteByUserId(int userId);
    
    // Additional methods can be added as needed
}
