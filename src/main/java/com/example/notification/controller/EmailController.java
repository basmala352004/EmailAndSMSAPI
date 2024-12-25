package com.example.notification.controller;

import com.example.notification.dto.EmailRequest;
import com.example.notification.model.EmailNotification;
import com.example.notification.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/email")
public class EmailController {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        EmailNotification emailNotification = emailNotificationService.sendEmail(request.getRecipient(), request.getSubject(), request.getMessage());

        if ("Sent".equals(emailNotification.getStatus())) {
            return ResponseEntity.ok("Email sent successfully to " + request.getRecipient());
        } else {
            return ResponseEntity.status(500).body("Failed to send email to " + request.getRecipient());
        }
    }
}
