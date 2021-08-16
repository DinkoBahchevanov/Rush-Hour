package com.example.module2.advices;

import com.example.module2.exceptions.appointments.AppointmentAlreadyMadeForChosenHourAndActivityException;
import com.example.module2.exceptions.appointments.AppointmentNotFoundException;
import com.example.module2.exceptions.appointments.InvalidAppointmentTimeException;
import com.example.module2.exceptions.appointments.UserAlreadyBusyInThisPeriodOfTimeException;
import com.example.module2.exceptions.users.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AppointmentControllerAdvice {

    @ExceptionHandler(AppointmentAlreadyMadeForChosenHourAndActivityException.class)
    public ResponseEntity<Object> handleAppointmentAlreadyCreatedForHourAndActivity(
            AppointmentAlreadyMadeForChosenHourAndActivityException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<Object> handleAppointmentInvalidStartTime(
            InvalidAppointmentTimeException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyBusyInThisPeriodOfTimeException.class)
    public ResponseEntity<Object> handleAppointmentInvalidStartTime(
            UserAlreadyBusyInThisPeriodOfTimeException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Object> handleAppointmentNotFoundWithId(
            AppointmentNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
