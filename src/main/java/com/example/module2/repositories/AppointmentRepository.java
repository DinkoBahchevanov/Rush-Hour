package com.example.module2.repositories;

import com.example.module2.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

//    @Query("select a from Appointment a join a.activities ac where ac.name=:activityName and (:startDateTime between a.startDateAndTime and a.endDateAndTime)")
@Query(
        value = "SELECT * from appointments as ap\n" +
                "JOIN activities_appointments as ac\n" +
                "on ap.id = ac.appointment_id\n" +
                "JOIN activities as a\n" +
                "on ac.activity_id = a.id\n" +
                "WHERE ap.start_date_time = :startDateTime and a.name = :activityName",
        nativeQuery = true)
    Appointment findAppointmentByStartDateAndTimeAndActivityName(@Param("startDateTime")LocalDateTime startDateTime,
                                                                 @Param("activityName") String activityName);

    @Query("SELECT a FROM Appointment a WHERE :startDateAndTime between a.startDateAndTime and a.endDateAndTime")
    Appointment findAppointmentByStartDateAndTimeBetween(@Param("startDateAndTime") LocalDateTime startDateAndTime);
}
