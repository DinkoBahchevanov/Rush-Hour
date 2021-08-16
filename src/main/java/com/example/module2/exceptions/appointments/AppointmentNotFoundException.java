package com.example.module2.exceptions.appointments;

import java.time.LocalDateTime;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(int appointmentId) {
        super(String.format("Appointment with id: %d not found", appointmentId));
    }
}
