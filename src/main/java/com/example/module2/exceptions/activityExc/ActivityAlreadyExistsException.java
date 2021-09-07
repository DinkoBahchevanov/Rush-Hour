package com.example.module2.exceptions.activityExc;

public class ActivityAlreadyExistsException extends RuntimeException {
    public ActivityAlreadyExistsException(String name) {
        super(String.format("Activity with name: %s - already exists", name));
    }
}
