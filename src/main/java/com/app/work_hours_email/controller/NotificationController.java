package com.app.work_hours_email.controller;

import com.app.work_hours_email.model.NotificationRequest;
import com.app.work_hours_email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
@CrossOrigin("*")  // allow extension calls
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendNotification(@RequestBody NotificationRequest request) {
        emailService.sendNotification(request);
        return "Email sent successfully!";
    }
}

