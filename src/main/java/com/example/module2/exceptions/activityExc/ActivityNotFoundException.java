package com.example.module2.exceptions.activityExc;

public class ActivityNotFoundException extends RuntimeException {

    public ActivityNotFoundException(int id) {
        super(String.format("Activity with ID: %d - not found", id));
    }
}
