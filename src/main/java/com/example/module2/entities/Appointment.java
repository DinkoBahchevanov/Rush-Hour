package com.example.module2.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {
    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private User user;
    private Set<Activity> activities;

    public Appointment(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, User user, Set<Activity> activities) {
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.user = user;
        this.activities = activities;
    }

    public Appointment() {
    }

    @Column(name = "start_date_time")
    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    @Column(name = "end_date_time")
    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }

    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    @ManyToOne()
    @JoinColumn(name="user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(
            name = "activities_appointments",
            joinColumns = @JoinColumn
                    (name = "appointment_id"),
            inverseJoinColumns = @JoinColumn
                    (name = "activity_id")
    )
    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
