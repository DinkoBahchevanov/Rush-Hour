package com.example.module2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "activities")
public class Activity extends BaseEntity{
    private String name;
    private double duration;
    private double price;
    private Set<Appointment> appointments;

    public Activity(String name, double duration, double price, Set<Appointment> appointments) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.appointments = appointments;
    }

    public Activity() {
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Column
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column
    @ManyToMany(mappedBy = "activities")
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

}
