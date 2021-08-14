package com.example.module2.exceptions.users;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {

        super("Not enough rights!");
    }
}
