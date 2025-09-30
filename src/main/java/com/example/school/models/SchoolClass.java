package com.example.school.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL) // Ensure this points to 'schoolClass' in Student
    private List<Student> students = new ArrayList<>();

    @OneToOne(mappedBy = "classAssigned", cascade = CascadeType.ALL) // Ensure this points to 'classAssigned' in Teacher
    private Teacher teacher;

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    
    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this); // Maintain bidirectional relationship
    }

    
    
}
