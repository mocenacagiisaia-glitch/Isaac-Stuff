package com.example.school.services;

import com.example.school.models.*;
import com.example.school.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ParentRepository parentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TermRepository termRepository;
    

    public void markAttendance(Student student, Term term, String status, int weekNumber, String day) {
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setTerm(term); // Associate with the term
        attendance.setStatus(status);
        attendance.setWeekNumber(weekNumber);
        attendance.setDay(day);
        attendance.setDate(LocalDate.now());

        attendanceRepository.save(attendance);

        if ("Absent".equals(status)) {
            notifyParents(student);
        }
    }

    public void saveAttendance(int studentId, Term term, Integer weekNumber, String day, String status) {
        // Retrieve the student by ID
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if attendance already exists for this student, week, and day
        Optional<Attendance> existingAttendance = attendanceRepository.findByStudentAndWeekNumberAndDay(student, weekNumber, day);

        if (existingAttendance.isPresent()) {
            // Update the existing attendance record
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            attendance.setDate(LocalDate.now()); // Optionally update the date
            attendance.setTerm(term); // Update the term if necessary
            attendanceRepository.save(attendance);
        } else {
            // Create a new attendance record
            markAttendance(student, term, status, weekNumber, day);
        }
    }




    private void notifyParents(Student student) {
        Parent parent1 = student.getParent1();
        Parent parent2 = student.getParent2();

        String notificationMessage = "Your child " + student.getFirstName() + " was marked absent.";
        LocalDateTime now = LocalDateTime.now();

        if (parent1 != null) {
            Notification notification = new Notification(notificationMessage, now);
            parent1.addNotification(notification);
            parentRepository.save(parent1);
        }
        if (parent2 != null) {
            Notification notification = new Notification(notificationMessage, now);
            parent2.addNotification(notification);
            parentRepository.save(parent2);
        }
    

    }

}
