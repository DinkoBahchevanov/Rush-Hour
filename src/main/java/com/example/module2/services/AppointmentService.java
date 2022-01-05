package com.example.module2.services;

import com.example.module2.web.dtos.appointmentDtos.AppointmentRequestDto;
import com.example.module2.web.dtos.appointmentDtos.AppointmentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface AppointmentService {

    AppointmentResponseDto create(AppointmentRequestDto appointmentRequestDto, String loggedInUserEmail);

    Set<AppointmentResponseDto> getAll();

    ResponseEntity<HttpStatus> delete(int appointmentId);

    AppointmentResponseDto update(int appointmentId, AppointmentRequestDto requestDto);
}
