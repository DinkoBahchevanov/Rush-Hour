package com.example.module2.web.dtos.appointmentDtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppointmentRequestDto {

    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private String userEmail;
    private ArrayList<String> activityNames;

    public AppointmentRequestDto(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, String userEmail, ArrayList<String> activityNames) {
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.userEmail = userEmail;
        this.activityNames = activityNames;
    }

    public AppointmentRequestDto(LocalDateTime startDateAndTime, String userEmail) {
        this.startDateAndTime = startDateAndTime;
        this.userEmail = userEmail;
    }

    public AppointmentRequestDto() {
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
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

    public ArrayList<String> getActivityNames() {
        return activityNames;
    }

    public void setActivityNames(ArrayList<String> activityNames) {
        this.activityNames = activityNames;
    }
}
