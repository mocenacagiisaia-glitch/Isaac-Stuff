package com.example.school.repositories;

import com.example.school.models.Attendance;
import com.example.school.models.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByStudentId(int studentId);
    Optional<Attendance> findByStudentAndWeekNumberAndDay(Student student, int weekNumber, String day);
    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.term.id = :termId ORDER BY a.weekNumber, a.date")
    List<Attendance> findByStudentIdAndTermId(int studentId, int termId);

    List<Attendance> findByStudent(Student student);


}
