package com.example.module2.exceptions.userExc;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {

        super(String.format("%s already exists!", email));
    }
}
