package com.example.school.services;

import com.example.school.models.Student;
import com.example.school.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Create or update a student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get a student by ID
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll(); // Should return the list of students
        System.out.println("Students: " + students); // Log to see if students are being retrieved
        return students;
    }


    // Delete a student by ID
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }
    
    public List<Student> getStudentsByIds(List<Integer> studentIds) {
        return studentRepository.findAllById(studentIds); // This will fetch students by their IDs
    }

}
