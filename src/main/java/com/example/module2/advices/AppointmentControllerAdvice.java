package com.example.module2.advices;

import com.example.module2.exceptions.appoinmtments.AppointmentAlreadyMadeForChosenHourAndActivityException;
import com.example.module2.exceptions.appoinmtments.AppointmentNotFoundException;
import com.example.module2.exceptions.appoinmtments.InvalidAppointmentTimeException;
import com.example.module2.exceptions.appoinmtments.UserAlreadyBusyInThisPeriodOfTimeException;
import com.example.module2.util.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentControllerAdvice {

    @ExceptionHandler(AppointmentAlreadyMadeForChosenHourAndActivityException.class)
    public ResponseEntity<Object> handleAppointmentAlreadyCreatedForHourAndActivity(AppointmentAlreadyMadeForChosenHourAndActivityException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<Object> handleAppointmentInvalidStartTime(InvalidAppointmentTimeException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyBusyInThisPeriodOfTimeException.class)
    public ResponseEntity<Object> handleAppointmentInvalidStartTime(UserAlreadyBusyInThisPeriodOfTimeException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Object> handleAppointmentNotFoundWithId(AppointmentNotFoundException ex) {

        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
