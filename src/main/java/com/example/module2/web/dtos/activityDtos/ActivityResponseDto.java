package com.example.module2.web.dtos.activityDtos;

public class ActivityResponseDto {
    private String name;
    private double duration;
    private double price;

    public ActivityResponseDto(String name, double duration, double price) {
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public ActivityResponseDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
