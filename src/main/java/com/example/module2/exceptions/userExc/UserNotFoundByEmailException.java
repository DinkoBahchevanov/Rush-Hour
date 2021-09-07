package com.example.module2.exceptions.userExc;

public class UserNotFoundByEmailException extends RuntimeException {

    public UserNotFoundByEmailException(String email) {

        super(String.format("User with email: %s - not found", email));
    }
}
