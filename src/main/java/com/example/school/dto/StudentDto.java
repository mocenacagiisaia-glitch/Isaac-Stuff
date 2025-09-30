package com.example.school.dto;

import java.time.LocalDate;

public class StudentDto {
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String parentName1; // First parent's name
    private String parentContact1; // First parent's contact
    private String parentEmail1; // First parent's email
    private String parentName2; // Second parent's name
    private String parentContact2; // Second parent's contact
    private String parentEmail2; // Second parent's email
    private String studentEmail; // New field for student's email

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getParentName1() { return parentName1; }
    public void setParentName1(String parentName1) { this.parentName1 = parentName1; }
    
    public String getParentContact1() { return parentContact1; }
    public void setParentContact1(String parentContact1) { this.parentContact1 = parentContact1; }
    
    public String getParentEmail1() { return parentEmail1; }
    public void setParentEmail1(String parentEmail1) { this.parentEmail1 = parentEmail1; }
    
    public String getParentName2() { return parentName2; }
    public void setParentName2(String parentName2) { this.parentName2 = parentName2; }
    
    public String getParentContact2() { return parentContact2; }
    public void setParentContact2(String parentContact2) { this.parentContact2 = parentContact2; }
    
    public String getParentEmail2() { return parentEmail2; }
    public void setParentEmail2(String parentEmail2) { this.parentEmail2 = parentEmail2; }
    
    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
}
