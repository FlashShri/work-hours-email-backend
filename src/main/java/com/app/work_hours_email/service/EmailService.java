package com.app.work_hours_email.service;



import com.app.work_hours_email.model.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(NotificationRequest request) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());

        String subject = "";
        String body = "";

        switch (request.getType()) {

            case "PUNCH_IN":
                subject = "Work Session Started";
                body = "You punched in at: " + request.getPunchInTime();
                break;

            case "TARGET_COMPLETED":
                subject = "Target Completed 🎉";
                body = "Congratulations! You completed your target hours.";
                break;

            case "PUNCH_OUT":
                subject = "Daily Work Report";
                body = """
                        Punch In: %s
                        Punch Out: %s
                        Working Hours: %s
                        Break Time: %s
                        Earnings: %s
                        """.formatted(
                        request.getPunchInTime(),
                        request.getPunchOutTime(),
                        request.getWorkingHours(),
                        request.getBreakTime(),
                        request.getEarnings()
                );
                break;

            default:
                subject = "Work Notification";
                body = "Notification received.";
        }

        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}

