package com.example.module2.exceptions.users;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {

        super(String.format("User with ID: %d - not found", id));
    }
}

