package com.example.module2.advices;

import com.example.module2.exceptions.userExc.AccessDeniedException;
import com.example.module2.exceptions.userExc.EmailAlreadyExistsException;
import com.example.module2.exceptions.userExc.UserNotFoundByEmailException;
import com.example.module2.exceptions.userExc.UserNotFoundByIdException;
import com.example.module2.util.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<Object> handleUserNotFoundByIdException(UserNotFoundByIdException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<Object> handleUserNotFoundByEmailException(
            UserNotFoundByEmailException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAddressAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ExceptionUtil.getBody(ex, HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }
}
