package com.example.module2.services;

import com.example.module2.entities.Activity;
import com.example.module2.entities.Appointment;
import com.example.module2.entities.User;
import com.example.module2.exceptions.appointments.AppointmentAlreadyMadeForChosenHourAndActivityException;
import com.example.module2.exceptions.appointments.AppointmentNotFoundException;
import com.example.module2.exceptions.appointments.InvalidAppointmentTimeException;
import com.example.module2.exceptions.appointments.UserAlreadyBusyInThisPeriodOfTimeException;
import com.example.module2.repositories.ActivityRepository;
import com.example.module2.repositories.AppointmentRepository;
import com.example.module2.repositories.UserRepository;
import com.example.module2.web.dtos.appointmentDtos.AppointmentMapper;
import com.example.module2.web.dtos.appointmentDtos.AppointmentRequestDto;
import com.example.module2.web.dtos.appointmentDtos.AppointmentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository,
                                  ActivityRepository activityRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto, String loggedInUserEmail) {
        Appointment appointment = new Appointment();
        appointment.setActivities(new HashSet<>());
        User loggedUser = userRepository.findByEmail(loggedInUserEmail);

        if (appointmentRepository.findAppointmentByStartDateAndTimeAndActivityName(
                appointmentRequestDto.getStartDateAndTime(), appointmentRequestDto.getActivityName()) != null) {
            throw new AppointmentAlreadyMadeForChosenHourAndActivityException(
                    appointmentRequestDto.getStartDateAndTime(), appointmentRequestDto.getActivityName());
        }
        if (appointmentRequestDto.getStartDateAndTime().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentTimeException(appointmentRequestDto.getStartDateAndTime(), LocalDateTime.now());
        }
        appointment.setStartDateAndTime(appointmentRequestDto.getStartDateAndTime());
        appointment.getActivities().add(activityRepository.findByName(appointmentRequestDto.getActivityName()));

        double duration = appointment.getActivities().stream().filter(
                activity -> activity.getName().contains(appointmentRequestDto.getActivityName())).findFirst().get().getDuration();
        appointment.setEndDateAndTime(appointment.getStartDateAndTime().plusHours((long) duration));
        appointment.setUser(userRepository.findByEmail(appointmentRequestDto.getUserEmail()));

        for ( Appointment loggedUserAppointment : loggedUser.getAppointments() ) {
            if (appointment.getStartDateAndTime().isAfter(loggedUserAppointment.getStartDateAndTime().minusMinutes(1)) &&
                    appointment.getStartDateAndTime().isBefore(loggedUserAppointment.getEndDateAndTime())) {
                throw new UserAlreadyBusyInThisPeriodOfTimeException(appointmentRequestDto.getUserEmail(), appointmentRequestDto.getStartDateAndTime());
            }
        }

        appointmentRepository.save(appointment);

        if (loggedUser.getAppointments().size() == 0) {
            loggedUser.setAppointments(new HashSet<>());
        }
        loggedUser.getAppointments().add(appointment);

        AppointmentResponseDto appointmentResponseDto = appointmentMapper.mapAppointmentToAppointmentResponseDto(appointment);
        appointmentResponseDto.setUserEmail(appointment.getUser().getEmail());
        appointmentResponseDto.setActivityNames(new HashSet<>(Set.of(appointmentRequestDto.getActivityName())));

        return appointmentResponseDto;
    }

    @Override
    public Set<AppointmentResponseDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        Set<AppointmentResponseDto> responseDtoList = new HashSet<>();
        for ( Appointment appointment : appointments ) {
            AppointmentResponseDto responseDto = appointmentMapper.mapAppointmentToAppointmentResponseDto(appointment);

            Set<String> activityNames = new HashSet<>();
            for ( Activity activity : appointment.getActivities() ) {
                activityNames.add(activity.getName());
            }
            responseDto.setActivityNames(activityNames);
            responseDto.setUserEmail(appointment.getUser().getEmail());
            responseDtoList.add(responseDto);
        }

        return new HashSet<>(responseDtoList);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAppointmentById(int appointmentId) {
        if (!appointmentRepository.findById(appointmentId).isPresent()) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public AppointmentResponseDto updateAppointmentById(int appointmentId, AppointmentRequestDto requestDto) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        Appointment alreadyHasApp = appointmentRepository.findAppointmentByStartDateAndTimeAndActivityName(
                requestDto.getStartDateAndTime(), requestDto.getActivityName());
        if (alreadyHasApp != null) {
            throw new AppointmentAlreadyMadeForChosenHourAndActivityException(
                    requestDto.getStartDateAndTime(), requestDto.getActivityName());
        }

        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        if (requestDto.getStartDateAndTime().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentTimeException(requestDto.getStartDateAndTime(), LocalDateTime.now());
        }

        Appointment appointmentByStartDateAndTime =
                appointmentRepository.findAppointmentByStartDateAndTimeBetween(requestDto.getStartDateAndTime());
        if (appointmentByStartDateAndTime != null) {
            if (appointmentByStartDateAndTime.getUser().getEmail().equals(requestDto.getUserEmail())) {
                throw new UserAlreadyBusyInThisPeriodOfTimeException(requestDto.getUserEmail(), requestDto.getStartDateAndTime());
            }
        }
        double duration = appointment.get().getActivities().stream().filter(
                activity -> activity.getName().contains(requestDto.getActivityName())).findFirst().get().getDuration();
        appointment.get().setEndDateAndTime(appointment.get().getStartDateAndTime().plusHours((long) duration));

//        appointment.get().getActivities().add(activityRepository.findByName(requestDto.getActivityName()));
//        appointment.get().setUser(userRepository.findByEmail(requestDto.getUserEmail()));

        return appointmentMapper.mapAppointmentToAppointmentResponseDto(
                appointmentRepository.save(appointment.get()));
    }
}
