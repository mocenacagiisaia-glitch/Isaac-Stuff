package com.example.school.services;

import com.example.school.models.SchoolClass;
import com.example.school.models.Student;
import com.example.school.models.Teacher;
import com.example.school.repositories.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    // Create or update a class
    public SchoolClass saveClass(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    // Get a class by ID
    public Optional<SchoolClass> getClassById(int id) {
        return schoolClassRepository.findById(id);
    }

    // Get all classes
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    // Delete a class by ID
    public void deleteClass(int id) {
        schoolClassRepository.deleteById(id);
    }
    // Check if a class with the same name exists
    public boolean existsByName(String name) {
        return schoolClassRepository.existsByName(name);
    }
    
    
    
    
}
