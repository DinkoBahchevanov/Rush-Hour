package com.example.module2.exceptions.userExc;

public class UserNotFoundByIdException extends RuntimeException {

    public UserNotFoundByIdException(int id) {

        super(String.format("User with ID: %d - not found", id));
    }
}

