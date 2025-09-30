package com.example.school.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendNotification(String email, String message) {
        // Mock email sending logic
        System.out.println("Sending email to " + email + ": " + message);
    }
}
