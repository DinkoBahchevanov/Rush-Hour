package com.example.module2.web.dtos.activityDtos;

import javax.validation.constraints.NotBlank;

public class ActivityRequestDto {

    private String name;
    private double duration;
    private double price;

    public ActivityRequestDto(String name, double duration, double price) {
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public ActivityRequestDto() {
    }

    @NotBlank(message = "Name is mandatory")
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
