package com.example.school.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String contact;

    // Tracks children where the parent is referenced as 'parent1'
    @OneToMany(mappedBy = "parent1", cascade = CascadeType.ALL)
    private List<Student> childrenAsParent1 = new ArrayList<>();

    // Tracks children where the parent is referenced as 'parent2'
    @OneToMany(mappedBy = "parent2", cascade = CascadeType.ALL)
    private List<Student> childrenAsParent2 = new ArrayList<>();

    // One-to-many relationship with Notification
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public List<Student> getChildrenAsParent1() { return childrenAsParent1; }
    public void setChildrenAsParent1(List<Student> childrenAsParent1) { this.childrenAsParent1 = childrenAsParent1; }

    public List<Student> getChildrenAsParent2() { return childrenAsParent2; }
    public void setChildrenAsParent2(List<Student> childrenAsParent2) { this.childrenAsParent2 = childrenAsParent2; }

    public List<Notification> getNotifications() { return notifications; }
    public void setNotifications(List<Notification> notifications) { this.notifications = notifications; }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setParent(this);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
        notification.setParent(null);
    }
}
