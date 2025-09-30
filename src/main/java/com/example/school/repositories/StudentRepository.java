package com.example.school.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.school.models.Student;

import jakarta.transaction.Transactional;

import com.example.school.models.SchoolClass;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    // Method to search for students by first or last name
    List<Student> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    // Method to find a student by email
    Student findByEmail(String email);

    // Method to find students by their assigned class
    List<Student> findBySchoolClass(SchoolClass schoolClass); // Updated to match the field in Student
    
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.id = :userId")
    void deleteByUserId(int userId);
}
