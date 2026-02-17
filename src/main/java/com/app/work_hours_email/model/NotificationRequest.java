package com.app.work_hours_email.model;


import lombok.Data;

@Data
public class NotificationRequest {

    private String type;          // PUNCH_IN / TARGET_COMPLETED / PUNCH_OUT
    private String email;

    private String punchInTime;
    private String punchOutTime;

    private String workingHours;
    private String breakTime;
    private String earnings;
}
