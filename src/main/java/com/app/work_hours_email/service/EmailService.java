package com.app.work_hours_email.service;




import com.app.work_hours_email.model.NotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendNotification(NotificationRequest request) {

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
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        Map<String, Object> payload = Map.of(
                "from", "Work Hours <onboarding@resend.dev>",
                "to", new String[]{request.getEmail()},
                "subject", subject,
                "text", body
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        restTemplate.exchange(
                "https://api.resend.com/emails",
                HttpMethod.POST,
                entity,
                String.class
        );
    }
}
