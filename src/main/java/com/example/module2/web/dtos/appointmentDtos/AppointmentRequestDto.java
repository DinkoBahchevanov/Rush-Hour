package com.example.module2.web.dtos.appointmentDtos;

import java.time.LocalDateTime;

public class AppointmentRequestDto {

    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private String userEmail;
    private String activityName;

    public AppointmentRequestDto(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, String userEmail, String activityName) {
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.userEmail = userEmail;
        this.activityName = activityName;
    }

    public AppointmentRequestDto() {
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }

    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
