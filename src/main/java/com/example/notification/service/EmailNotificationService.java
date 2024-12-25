package com.example.notification.service;

import com.example.notification.model.EmailNotification;
import com.example.notification.repository.EmailNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailNotificationService {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    public EmailNotification sendEmail(String recipient, String subject, String message) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setRecipient(recipient);
        emailNotification.setSubject(subject);
        emailNotification.setMessage(message);
        emailNotification.setCreatedAt(LocalDateTime.now());
        emailNotification.setStatus("Pending");

        try {
            boolean success = sendEmailToRecipient(emailNotification);
            emailNotification.setStatus(success ? "Sent" : "Failed");
        } catch (Exception e) {
            emailNotification.setStatus("Failed");
            System.err.println("Error sending email: " + e.getMessage());
            // Log the error using a logging framework like SLF4J or Logback
        }

        return emailNotificationRepository.save(emailNotification);
    }

    private boolean sendEmailToRecipient(EmailNotification emailNotification) {
        // Simulate sending email
        System.out.println("Sending email to " + emailNotification.getRecipient());
        System.out.println("Subject: " + emailNotification.getSubject());
        System.out.println("Message: " + emailNotification.getMessage());

        // Simulate success or failure
        return true; // Change to false to simulate failure
    }
}
