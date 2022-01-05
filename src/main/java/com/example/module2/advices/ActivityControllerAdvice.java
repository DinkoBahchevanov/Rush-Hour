package com.example.module2.advices;

import com.example.module2.exceptions.activityExc.ActivityAlreadyExistsException;
import com.example.module2.exceptions.activityExc.ActivityNotFoundByNameException;
import com.example.module2.exceptions.activityExc.ActivityNotFoundException;
import com.example.module2.util.ExceptionUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ActivityControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<Object> handleActivityNotFoundException(ActivityNotFoundException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActivityAlreadyExistsException.class)
    public ResponseEntity<Object> handleActivityAlreadyExistsException(ActivityAlreadyExistsException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActivityNotFoundByNameException.class)
    public ResponseEntity<Object> handleActivityNotFoundByNameException(ActivityNotFoundByNameException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
