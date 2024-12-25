package com.example.notification.service;

import com.example.notification.model.SmsNotification;
import com.example.notification.repository.SmsNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SmsNotificationService {

    @Autowired
    private SmsNotificationRepository smsNotificationRepository;

    public SmsNotification sendSms(String recipient, String message) {
        SmsNotification smsNotification = new SmsNotification();
        smsNotification.setRecipient(recipient);
        smsNotification.setMessage(message);
        smsNotification.setCreatedAt(LocalDateTime.now());
        smsNotification.setStatus("Pending");

        try {
            boolean success = sendSmsToRecipient(smsNotification);
            smsNotification.setStatus(success ? "Sent" : "Failed");
        } catch (Exception e) {
            smsNotification.setStatus("Failed");
            System.err.println("Error sending SMS: " + e.getMessage());
            // Log the error using a logging framework like SLF4J or Logback
        }

        return smsNotificationRepository.save(smsNotification);
    }

    private boolean sendSmsToRecipient(SmsNotification smsNotification) {
        // Simulate sending SMS
        System.out.println("Sending SMS to " + smsNotification.getRecipient());
        System.out.println("Message: " + smsNotification.getMessage());

        // Simulate success or failure
        return true; // Change to false to simulate failure
    }
}
