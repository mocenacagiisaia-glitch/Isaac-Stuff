package com.example.school.services;

import com.example.school.models.Teacher;
import com.example.school.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Create or update a teacher
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Get a teacher by ID
    public Optional<Teacher> getTeacherById(int id) {
        return teacherRepository.findById(id);
    }

    // Get all teachers
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Delete a teacher by ID
    public void deleteTeacher(int id) {
        teacherRepository.deleteById(id);
    }

    // Find a teacher by email
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }
}
