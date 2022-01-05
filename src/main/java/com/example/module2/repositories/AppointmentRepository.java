package com.example.module2.repositories;

import com.example.module2.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query(
            value = "SELECT * from appointments as ap\n" +
                    "JOIN activities_appointments as ac\n" +
                    "on ap.id = ac.appointment_id\n" +
                    "JOIN activities as a\n" +
                    "on ac.activity_id = a.id\n" +
                    "WHERE ap.start_date_time = :startDateTime and a.name = :activityName",
            nativeQuery = true)
    Appointment findAppointmentByStartDateAndTimeAndActivityName(@Param("startDateTime") LocalDateTime startDateTime,
                                                                 @Param("activityName") String activityName);

    @Query("SELECT a FROM Appointment a WHERE :startDateAndTime between a.startDateAndTime and a.endDateAndTime OR " +
            ":endDateAndTime between a.startDateAndTime and a.endDateAndTime")
    Set<Appointment> findAppointmentByDateAndTimeBetween(@Param("startDateAndTime") LocalDateTime startDateAndTime,
                                                         @Param("endDateAndTime") LocalDateTime endDateAndTime);
}
