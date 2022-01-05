package com.example.module2.exceptions.userExc;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(int id) {

        super(String.format("Not enough rights for the user with id: %d", id));
    }
}
