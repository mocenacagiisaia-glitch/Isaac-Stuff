package com.example.school.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import com.example.school.models.Parent;
import com.example.school.models.Student;
import com.example.school.models.Teacher; // Adjust the import as per your package structure
import com.example.school.repositories.ParentRepository;
import com.example.school.repositories.StudentRepository;
import com.example.school.services.TeacherService; // Import your service to fetch teacher data

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TeacherService teacherService;
    private final ParentRepository parentRepository; // Declare the ParentRepository
    private final StudentRepository studentRepository;


    // Constructor injection for TeacherService and ParentRepository
    public CustomLoginSuccessHandler(TeacherService teacherService, ParentRepository parentRepository,StudentRepository studentRepository) {
        this.teacherService = teacherService;
        this.parentRepository = parentRepository; // Initialize the ParentRepository
        this.studentRepository = studentRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String redirectUrl = determineTargetUrl(authentication);
        response.sendRedirect(redirectUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check the role and determine the target URL
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            switch (role) {
                case "ADMIN":
                    return "/admin/dashboard";
                case "TEACHER":
                    return "/teacher/home"; // Redirect with teacher ID
                case "PARENT":
                    return "/parent/home"; // Redirect with parent ID
                case "STUDENT":
                    return "/student/home"; // Redirect with student ID
                default:
                    return "/"; // Fallback URL if role doesn't match
            }
        }
        // If no role is found, redirect to home
        return "/";
    }

    private String redirectParentDashboard(String email) {
        // Fetch the parent by email to get the parent ID
        Parent parent = parentRepository.findByEmail(email);
        if (parent != null) {
            // Redirect to the parent's dashboard with their ID
            return "/parent/dashboard?parentId=" + parent.getId();
        }
        // Fallback if parent not found
        return "/";
    }

    private String redirectTeacherDashboard(String email) {
        // Fetch the teacher by email to get the teacher ID
        Teacher teacher = teacherService.findByEmail(email);
        if (teacher != null) {
            // Redirect to the teacher's dashboard with their ID
            return "/teacher/dashboard/" + teacher.getId();
        }
        // Fallback if teacher not found
        return "/";
    }
    
    private String redirectStudentDashboard(String email) {
        // Fetch the student by email to get the student ID
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            // Redirect to the student's dashboard with their ID using path variable
            return "/student/dashboard/" + student.getId(); // Changed to path variable
        }
        // Fallback if student not found
        return "/";
    }

}
