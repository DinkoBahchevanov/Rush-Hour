package com.example.module2.web.dtos.appointmentDtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public class AppointmentResponseDto {
    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private String userEmail;
    private Set<String> activityNames;

    public AppointmentResponseDto(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, String userEmail, Set<String> activityNames) {
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.userEmail = userEmail;
        this.activityNames = activityNames;
    }

    public AppointmentResponseDto() {
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
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

    public Set<String> getActivityNames() {
        return activityNames;
    }

    public void setActivityNames(Set<String> activityNames) {
        this.activityNames = activityNames;
    }
}
