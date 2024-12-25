package com.example.notification.controller;

import com.example.notification.dto.SmsRequest;
import com.example.notification.model.SmsNotification;
import com.example.notification.service.SmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/sms")
public class SmsController {

    @Autowired
    private SmsNotificationService smsNotificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request) {
        SmsNotification smsNotification = smsNotificationService.sendSms(request.getRecipient(), request.getMessage());

        if ("Sent".equals(smsNotification.getStatus())) {
            return ResponseEntity.ok("SMS sent successfully to " + request.getRecipient());
        } else {
            return ResponseEntity.status(500).body("Failed to send SMS to " + request.getRecipient());
        }
    }
}
