package com.example.module2.services;

import com.example.module2.entities.Appointment;
import com.example.module2.web.dtos.appointmentDtos.AppointmentRequestDto;
import com.example.module2.web.dtos.appointmentDtos.AppointmentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface AppointmentService {

    AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto, String loggedInUserEmail);

    Set<AppointmentResponseDto> getAllAppointments();

    ResponseEntity<HttpStatus> deleteAppointmentById(int appointmentId);

    AppointmentResponseDto updateAppointmentById(int appointmentId, AppointmentRequestDto requestDto);
}
