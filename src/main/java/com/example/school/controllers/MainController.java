package com.example.school.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.school.models.Student;
import com.example.school.models.Term; // Import your Term model
import com.example.school.services.ClassService;
import com.example.school.services.StudentService;
import com.example.school.services.TeacherService;
import com.example.school.services.TermService; // Import your TermService

@Controller
public class MainController {
    
    @Autowired
    private ClassService classService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TermService termService; // Add TermService

    // Home Page
    @GetMapping("/")
    public String home() {
        return "index"; // This will return the index.html template
    }

    
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard"; // This will return the admin_dashboard.html template
    }


    
    @GetMapping("/admin/create-student")
    public String createStudent() {
        return "create_student"; // This will return the create_student.html template
    }
    
    @GetMapping("/admin/create-teacher")
    public String createTeacher() {
        return "create-teacher"; // This will return the create_teacher.html template
    }
    
    @GetMapping("/admin/create-class")
    public String showCreateClassForm(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("students", studentService.getAllStudents());
        return "create_class"; // Ensure this path matches your templates
    }
    
    // Show Create Term Form
    @GetMapping("/admin/create-term")
    public String showCreateTermForm(Model model) {
        return "create_term"; // Path to your create_term.html template
    }
    
    @GetMapping("/admin/update-account")
    public String updateStudentTeacher() {
        return "updateStudentTeacher"; // Ensure this matches your templates
    }
    

    
    @GetMapping("auth/reset-password")
    public String showResetPasswordPage() {
        return "reset_password"; // Ensure this matches your templates
    }

    @GetMapping("/admin/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents(); // Method to fetch all students
        model.addAttribute("students", students);
        return "student_list"; // Name of the HTML template
    }

    @GetMapping("/admin/students/{id}")
    public String viewStudentProfile(@PathVariable int id, Model model) {
        Optional<Student> optionalStudent = studentService.getStudentById(id); // Get the Optional<Student>

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get(); // Get the Student from the Optional
            model.addAttribute("student", student);
            return "student_profile"; // Template for the student's profile
        } else {
            // Handle the case where the student is not found
            return "redirect:/admin/students"; // Redirect to the student list, or you can show an error page
        }
    }
    
    
}
