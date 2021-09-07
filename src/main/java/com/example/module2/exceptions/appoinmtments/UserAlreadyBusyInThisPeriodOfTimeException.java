package com.example.module2.exceptions.appoinmtments;

import java.time.LocalDateTime;

public class UserAlreadyBusyInThisPeriodOfTimeException extends RuntimeException {

    public UserAlreadyBusyInThisPeriodOfTimeException(String userEmail, LocalDateTime startDateTime) {
        super(String.format("User: %s already has appointment for that time: %s.",userEmail, startDateTime.toString()));
    }
}
