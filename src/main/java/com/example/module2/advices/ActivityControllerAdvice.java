package com.example.module2.advices;

import com.example.module2.exceptions.activities.ActivityAlreadyExistsException;
import com.example.module2.exceptions.activities.ActivityNotFoundException;
import com.example.module2.exceptions.users.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ActivityControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<Object> handleActivityNotFoundException(
            ActivityNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Activity not found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ActivityAlreadyExistsException.class)
    public ResponseEntity<Object> handleActivityAlreadyExistsException(
            ActivityAlreadyExistsException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
