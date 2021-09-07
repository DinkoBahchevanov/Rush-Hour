package com.example.module2.exceptions.appoinmtments;

import java.time.LocalDateTime;

public class AppointmentAlreadyMadeForChosenHourAndActivityException extends RuntimeException {

    public AppointmentAlreadyMadeForChosenHourAndActivityException(LocalDateTime startDateTime, String activityName) {
        super(String.format("Appointment during start-time: %s and activity name: %s - already exists",startDateTime.toString(), activityName));
    }
}
