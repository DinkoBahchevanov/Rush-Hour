package com.example.module2.services;

import com.example.module2.entities.Appointment;
import com.example.module2.web.dtos.appointmentDtos.AppointmentRequestDto;
import com.example.module2.web.dtos.appointmentDtos.AppointmentResponseDto;

import java.util.Set;

public interface AppointmentService {

    AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto, String loggedInUserEmail);

    Set<AppointmentResponseDto> getAllAppointments();
}
