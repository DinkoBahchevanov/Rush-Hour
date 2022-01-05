package com.example.module2.exceptions.activityExc;

public class ActivityNotFoundByNameException extends RuntimeException {

    public ActivityNotFoundByNameException(String name) {
        super(String.format("Activity with name: %s -  not found!", name));
    }
}
