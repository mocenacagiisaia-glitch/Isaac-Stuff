package com.example.school.controllers;

import com.example.school.dto.StudentDto;
import com.example.school.dto.TeacherDto;
import com.example.school.models.*;
import com.example.school.repositories.*;
import com.example.school.services.ClassService;
import com.example.school.services.StudentService;
import com.example.school.services.TeacherService;
import com.example.school.services.TermService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.school.models.SchoolClass;
import com.example.school.models.Student;
import com.example.school.services.ClassService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private RoleRepository roleRepository; // Add RoleRepository to fetch roles

    @Autowired
    private ClassService classService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TermService termService;

    // Method to create a student
    @PostMapping("/create-student")
    public ResponseEntity<Map<String, String>> createStudent(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String gender,
            @RequestParam LocalDate dateOfBirth,
            @RequestParam String address,
            @RequestParam String parentName1,
            @RequestParam String parentContact1,
            @RequestParam String parentEmail1,
            @RequestParam String parentName2,
            @RequestParam String parentContact2,
            @RequestParam String parentEmail2,
            @RequestParam String studentEmail) {

        Map<String, String> response = new HashMap<>();

        try {
            // Check for existing emails
            if (userRepository.existsByEmail(parentEmail1)) {
                response.put("success", "false");
                response.put("message", "Parent 1 email already exists!");
                return ResponseEntity.badRequest().body(response);
            }
            if (userRepository.existsByEmail(parentEmail2)) {
                response.put("success", "false");
                response.put("message", "Parent 2 email already exists!");
                return ResponseEntity.badRequest().body(response);
            }
            if (userRepository.existsByEmail(studentEmail)) {
                response.put("success", "false");
                response.put("message", "Student email already exists!");
                return ResponseEntity.badRequest().body(response);
            }

            // Create and save the first Parent
            Parent parent1 = new Parent();
            parent1.setName(parentName1);
            parent1.setContact(parentContact1);
            parent1.setEmail(parentEmail1);
            parentRepository.save(parent1);

            // Create and save the second Parent
            Parent parent2 = new Parent();
            parent2.setName(parentName2);
            parent2.setContact(parentContact2);
            parent2.setEmail(parentEmail2);
            parentRepository.save(parent2);

            // Create and save the Student
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setGender(gender);
            student.setDateOfBirth(dateOfBirth);
            student.setAddress(address);
            student.setParent1(parent1); // Link to the first parent
            student.setParent2(parent2); // Link to the second parent
            student.setEmail(studentEmail); // Set the student's email

            studentRepository.save(student);

            // Create User for the first Parent
            UserEntity parentUser1 = new UserEntity();
            parentUser1.setEmail(parentEmail1);
            parentUser1.setPassword(passwordEncoder.encode("password"));

            Role parentRole = roleRepository.findByName("PARENT").orElseThrow(() ->
                new RuntimeException("Role PARENT not found")
            );
            parentUser1.setRoles(Collections.singletonList(parentRole));
            userRepository.save(parentUser1);

            // Create User for the second Parent
            UserEntity parentUser2 = new UserEntity();
            parentUser2.setEmail(parentEmail2);
            parentUser2.setPassword(passwordEncoder.encode("password"));
            parentUser2.setRoles(Collections.singletonList(parentRole));
            userRepository.save(parentUser2);

            // Create User for Student
            UserEntity studentUser = new UserEntity();
            studentUser.setEmail(studentEmail); // Use the provided student email
            studentUser.setPassword(passwordEncoder.encode("password"));

            Role studentRole = roleRepository.findByName("STUDENT").orElseThrow(() ->
                new RuntimeException("Role STUDENT not found")
            );
            studentUser.setRoles(Collections.singletonList(studentRole));
            userRepository.save(studentUser);

            response.put("success", "true");
            response.put("message", "Student and parents created successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error creating student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Method to create a teacher
    @PostMapping("/create-teacher")
    public ResponseEntity<Map<String, String>> createTeacher(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam(required = false) String contact) {

        Map<String, String> response = new HashMap<>();

        try {
            if (userRepository.existsByEmail(email)) {
                response.put("success", "false");
                response.put("message", "Email already exists in the system!");
                return ResponseEntity.badRequest().body(response);
            }

            if (teacherRepository.existsByEmail(email)) {
                response.put("success", "false");
                response.put("message", "Email already exists for another teacher!");
                return ResponseEntity.badRequest().body(response);
            }

            Teacher teacher = new Teacher();
            teacher.setFirstName(firstName);
            teacher.setLastName(lastName);
            teacher.setEmail(email);
            teacher.setContact(contact);

            teacherRepository.save(teacher);

            UserEntity teacherUser = new UserEntity();
            teacherUser.setEmail(email);
            teacherUser.setPassword(passwordEncoder.encode("password"));

            Role teacherRole = roleRepository.findByName("TEACHER").orElseThrow(() ->
                new RuntimeException("Role TEACHER not found")
            );
            teacherUser.setRoles(Collections.singletonList(teacherRole));
            userRepository.save(teacherUser);

            response.put("success", "true");
            response.put("message", "Teacher created successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error creating teacher: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/create-class")
    public ResponseEntity<String> createClass(@RequestParam String name,
                                              @RequestParam int teacherId,
                                              @RequestParam List<Integer> studentIds) {
        try {
            // Check if a class with the same name already exists
            if (classService.existsByName(name)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Class with this name already exists!");
            }

            // Create a new SchoolClass object
            SchoolClass newClass = new SchoolClass();
            newClass.setName(name);

            // Fetch the teacher and assign
            Teacher teacher = teacherService.getTeacherById(teacherId).orElseThrow(() -> 
                new RuntimeException("Teacher not found"));

            // Check if the teacher is already assigned to a class
            if (teacher.getClassAssigned() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Teacher " + teacher.getFirstName() + " " + teacher.getLastName() + " is already assigned to a class.");
            }

            newClass.setTeacher(teacher); // Assign the teacher to the new class
            teacher.setClassAssigned(newClass); // Link the teacher to the new class
            
            // Assign students to the class
            for (int studentId : studentIds) {
                Student student = studentService.getStudentById(studentId).orElseThrow(() -> 
                    new RuntimeException("Student not found"));

                // Check if the student is already assigned to a class
                if (student.getSchoolClass() != null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                         .body("Student " + student.getFirstName() + " " + student.getLastName() + " is already assigned to a class.");
                }

                student.setSchoolClass(newClass); // Link the student to the new class
            }

            // Save the class and update teacher and students
            classService.saveClass(newClass);
            teacherRepository.save(teacher); // Save the updated teacher entity

            return ResponseEntity.ok("Class created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating class: " + e.getMessage());
        }
    }

    @PostMapping("/create-term")
    public ResponseEntity<Map<String, String>> createTerm(
            @RequestParam String name,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam int durationInWeeks) {

        Map<String, String> response = new HashMap<>();

        try {
            LocalDate endDate = startDate.plusWeeks(durationInWeeks);

            Term term = new Term();
            term.setName(name);
            term.setStartDate(startDate);
            term.setEndDate(endDate);
            term.setDurationInWeeks(durationInWeeks); // Ensure duration is set

            termService.saveTerm(term);

            response.put("success", "true");
            response.put("message", "Term created successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error creating term: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    


 // Method to search students
    @GetMapping("/search-students")
    public List<Student> searchStudents(@RequestParam String query) {
        return studentRepository.findByFirstNameContainingOrLastNameContaining(query, query);
    }

    // Method to search teachers
    @GetMapping("/search-teachers")
    public List<Teacher> searchTeachers(@RequestParam String query) {
        return teacherRepository.findByFirstNameContainingOrLastNameContaining(query, query);
    }

    // Method to update a student
    @PutMapping("/update-student")
    public ResponseEntity<Map<String, String>> updateStudent(
            @RequestParam int id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String gender,
            @RequestParam LocalDate dateOfBirth,
            @RequestParam String address,
            @RequestParam String email,
            @RequestParam(required = false) String contact) {

        Map<String, String> response = new HashMap<>();

        try {
            Student student = studentRepository.findById(id).orElseThrow(() -> 
                new RuntimeException("Student not found"));
            
            // Update student details
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setGender(gender);
            student.setDateOfBirth(dateOfBirth);
            student.setAddress(address);
            student.setEmail(email);

            studentRepository.save(student);

            response.put("success", "true");
            response.put("message", "Student updated successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error updating student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PutMapping("/update-teacher")
    public ResponseEntity<Map<String, String>> updateTeacher(
            @RequestParam int id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam(required = false) String contact) {

        Map<String, String> response = new HashMap<>();

        try {
            // Find the existing teacher by ID
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            // Check for email conflicts
            if (!teacher.getEmail().equals(email) && userRepository.existsByEmail(email)) {
                response.put("success", "false");
                response.put("message", "Email already exists in the system!");
                return ResponseEntity.badRequest().body(response);
            }

            // Update teacher details
            teacher.setFirstName(firstName);
            teacher.setLastName(lastName);
            teacher.setEmail(email);
            teacher.setContact(contact); // Update contact info

            // Save updated teacher object
            teacherRepository.save(teacher);

            // Update user email if changed
            if (!teacher.getEmail().equals(email)) {
                UserEntity teacherUser = userRepository.findByEmail(teacher.getEmail())
                        .orElseThrow(() -> new RuntimeException("User for teacher not found"));
                teacherUser.setEmail(email);
                userRepository.save(teacherUser);
            }

            // Prepare response for successful update
            response.put("success", "true");
            response.put("message", "Teacher updated successfully!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error updating teacher: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
   

    

 
}
   

