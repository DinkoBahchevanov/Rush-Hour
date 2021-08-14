package com.example.module2.exceptions.activities;

public class ActivityNotFoundException extends RuntimeException {

    public ActivityNotFoundException(int id) {
        super(String.format("Activity with ID: %d - not found", id));
    }
}
