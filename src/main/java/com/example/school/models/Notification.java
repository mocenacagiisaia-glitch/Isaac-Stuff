package com.example.school.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;
    private LocalDateTime date;

    @Column(name = "is_read", nullable = false) // Update column name to avoid conflicts with SQL reserved keywords
    private boolean isRead; // Renamed from `read` to `isRead`

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    // Constructors, Getters, and Setters
    public Notification() {}

    public Notification(String message, LocalDateTime date) {
        this.message = message;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean isRead) { this.isRead = isRead; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }
}
