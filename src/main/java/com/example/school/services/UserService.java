package com.example.school.services;

import com.example.school.models.UserEntity;
import com.example.school.repositories.ParentRepository;
import com.example.school.repositories.StudentRepository;
import com.example.school.repositories.TeacherRepository;
import com.example.school.repositories.UserRepository; // Make sure to have a UserRepository interface

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private ParentRepository parentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    
    

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll(); // Fetch all users from the repository
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id); // Delete user by ID
    }
    
   
    
    public void deleteUserWithRoles(int userId) {
        // Find the user and remove their roles
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Clear roles
        user.getRoles().clear();

        // Save the user to persist the changes
        userRepository.save(user);

        // Now delete the user
        userRepository.deleteById(userId);
}
    
    @Transactional
    public void deleteUser(int userId) {
        // Optionally, check if the user exists
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            // Remove any associated Teacher or Student entries if applicable
            teacherRepository.deleteByUserId(userId); // implement this in TeacherRepository
            studentRepository.deleteByUserId(userId); // implement this in StudentRepository
            parentRepository.deleteByUserId(userId); // implement this in StudentRepository

            
            // Finally, delete the user
            userRepository.deleteById(userId);
        }
    
    }
}
