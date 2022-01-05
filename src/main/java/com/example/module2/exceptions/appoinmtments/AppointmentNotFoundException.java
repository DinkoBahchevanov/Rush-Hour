package com.example.module2.exceptions.appoinmtments;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(int appointmentId) {
        super(String.format("Appointment with id: %d not found", appointmentId));
    }
}
