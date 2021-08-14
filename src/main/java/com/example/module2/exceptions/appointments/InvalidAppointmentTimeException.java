package com.example.module2.exceptions.appointments;

import java.time.LocalDateTime;

public class InvalidAppointmentTimeException extends RuntimeException {

    public InvalidAppointmentTimeException(LocalDateTime startDateTime, LocalDateTime currentTime) {
        super(String.format("Invalid %s time for currentTime: %s", startDateTime.toString(), currentTime.toString()));
    }
}
