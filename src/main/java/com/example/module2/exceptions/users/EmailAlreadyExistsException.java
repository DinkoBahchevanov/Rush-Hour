package com.example.module2.exceptions.users;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {

        super(String.format("%s already exists!", email));
    }
}
