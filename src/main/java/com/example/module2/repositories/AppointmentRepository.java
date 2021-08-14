package com.example.module2.repositories;

import com.example.module2.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("select a from Appointment a join a.activities ac where :startDateTime between a.startDateAndTime and a.endDateAndTime and ac.name=:activityName")
    Appointment findAppointmentByStartDateAndTimeAndActivityName(@Param("startDateTime")LocalDateTime startDateTime,
                                                                 @Param("activityName") String activityName);
}
