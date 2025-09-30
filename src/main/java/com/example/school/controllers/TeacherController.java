package com.example.school.controllers;

import com.example.school.models.*;
import com.example.school.repositories.TermRepository;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.school.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller // Keep this as @Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TermService termService;
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private TermRepository termRepository;
    
    @GetMapping("/dashboard/{teacherId}") // This will capture the teacher ID from the URL
    public String teacherDashboard(Model model, @PathVariable int teacherId) {
        // Fetch teacher by ID
        Teacher teacher = teacherService.getTeacherById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Pass teacher and assigned class to the model
        model.addAttribute("teacher", teacher);
        model.addAttribute("classAssigned", teacher.getClassAssigned());

        return "teacher_dashboard";  // Render the teacher dashboard template
    }

    @GetMapping("/attendance/{teacherId}")
    public String showAttendanceForm(@PathVariable int teacherId, Model model) {
        // Fetch teacher by ID
        Teacher teacher = teacherService.getTeacherById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Add teacher and other required attributes to the model
        model.addAttribute("teacher", teacher);
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("students", studentService.getAllStudents());

        return "teacher_attendance"; // Ensure this matches your Thymeleaf template
    }


    @PostMapping("/take-attendance")
    public ResponseEntity<String> takeAttendance(
            @RequestParam("termId") Long termId, // Change to Long if Term ID is Long
            @RequestParam("weekNumber") Integer weekNumber,
            @RequestParam("day") String day,
            @RequestParam Map<String, String> attendanceStatus) {

        System.out.println("Term ID: " + termId);
        System.out.println("Week Number: " + weekNumber);
        System.out.println("Day: " + day);
        System.out.println("Attendance Status: " + attendanceStatus);

        // Retrieve the Term by ID
        Term term = termRepository.findById(termId)
                .orElse(null);

        if (term == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Term ID!");
        }

        // Process attendance data for each student
        attendanceStatus.forEach((key, status) -> {
            if (key.startsWith("attendanceStatus[")) {
                String studentIdString = key.replace("attendanceStatus[", "").replace("]", "");
                try {
                    int studentId = Integer.parseInt(studentIdString);
                    System.out.println("Student ID: " + studentId + ", Status: " + status);
                    attendanceService.saveAttendance(studentId, term, weekNumber, day, status);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid student ID format for key: " + key);
                }
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body("Attendance recorded successfully!");
    }




}
