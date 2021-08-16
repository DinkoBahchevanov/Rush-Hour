package com.example.module2.web.controllers;

import com.example.module2.entities.Appointment;
import com.example.module2.services.AppointmentService;
import com.example.module2.web.dtos.appointmentDtos.AppointmentRequestDto;
import com.example.module2.web.dtos.appointmentDtos.AppointmentResponseDto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponseDto createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto
            , Authentication authentication) {
        return appointmentService.createAppointment(appointmentRequestDto, authentication.getName());
    }

    @GetMapping
    public Set<AppointmentResponseDto> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<HttpStatus> deleteAppointmentById(@PathVariable int appointmentId) {
        return appointmentService.deleteAppointmentById(appointmentId);
    }

    @PutMapping("/{appointmentId}")
    public AppointmentResponseDto updateAppointmentById(@PathVariable int appointmentId) {
        return appointmentService.updateAppointmentById(appointmentId);
    }
}
