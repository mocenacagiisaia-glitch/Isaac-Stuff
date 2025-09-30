package com.example.school.models;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName; // Change to first name
    private String lastName; // Change to last name
    private String email;
    private String contact;

    @OneToOne // A teacher can be assigned to only one class
    @JoinColumn(name = "class_id") // This column in the database will reference the SchoolClass
    private SchoolClass classAssigned;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() { // Getter for first name
        return firstName;
    }

    public void setFirstName(String firstName) { // Setter for first name
        this.firstName = firstName;
    }

    public String getLastName() { // Getter for last name
        return lastName;
    }

    public void setLastName(String lastName) { // Setter for last name
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public SchoolClass getClassAssigned() {
        return classAssigned;
    }

    public void setClassAssigned(SchoolClass classAssigned) {
        this.classAssigned = classAssigned;
    }
}
