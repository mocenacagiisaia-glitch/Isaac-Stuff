package com.example.school.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.school.models.Attendance;
import com.example.school.models.Parent;
import com.example.school.models.Student;
import com.example.school.repositories.AttendanceRepository;
import com.example.school.repositories.ParentRepository;
import com.example.school.repositories.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ParentRepository parentRepository;

    @GetMapping("/student/dashboard/{studentId}") // This captures the studentId from the URL
    public String showStudentDashboard(@PathVariable("studentId") int studentId, Model model) {
        // Log the received studentId for debugging
        System.out.println("Received studentId: " + studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Add student to model
        model.addAttribute("student", student);
        
        return "student_dashboard"; // Renders student_dashboard.html
    }


    // Show Student Attendance
    @GetMapping("/student/attendance")
    public String showStudentAttendance(@RequestParam("studentId") int studentId, Model model) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get attendance records for the student
        List<Attendance> attendanceRecords = attendanceRepository.findByStudent(student);

        // Add the student and their attendance records to the model
        model.addAttribute("student", student);
        model.addAttribute("attendanceRecords", attendanceRecords);

        return "student_attendance"; // Renders student_attendance.html
    }

 
}
