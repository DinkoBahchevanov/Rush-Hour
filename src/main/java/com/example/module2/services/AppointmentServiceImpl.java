package com.example.module2.services;

import com.example.module2.entities.Activity;
import com.example.module2.entities.Appointment;
import com.example.module2.entities.User;
import com.example.module2.exceptions.activityExc.ActivityNotFoundByNameException;
import com.example.module2.exceptions.appoinmtments.AppointmentAlreadyMadeForChosenHourAndActivityException;
import com.example.module2.exceptions.appoinmtments.AppointmentNotFoundException;
import com.example.module2.exceptions.appoinmtments.InvalidAppointmentTimeException;
import com.example.module2.exceptions.appoinmtments.UserAlreadyBusyInThisPeriodOfTimeException;
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
import java.util.*;

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
    public AppointmentResponseDto create(AppointmentRequestDto appointmentRequestDto, String loggedInUserEmail) {
        User loggedUser = userRepository.findByEmail(loggedInUserEmail);

        Appointment createdAppointment = new Appointment();

        createdAppointment.setStartDateAndTime(appointmentRequestDto.getStartDateAndTime());
        double duration = 0;
        for ( String activityName : appointmentRequestDto.getActivityNames() ) {
            Activity activity = activityRepository.findByName(activityName);
            if (activity == null) {
                throw new ActivityNotFoundByNameException(activityName);
            }
            duration += activity.getDuration();
        }
        createdAppointment.setEndDateAndTime((createdAppointment.getStartDateAndTime().plusHours((long) duration)));
        Set<Appointment> appointmentsBetweenGivenStartAndEndTime = appointmentRepository.findAppointmentByDateAndTimeBetween(appointmentRequestDto.getStartDateAndTime(), createdAppointment.getEndDateAndTime());

        for ( Appointment appointment : appointmentsBetweenGivenStartAndEndTime ) {
            if (appointment.getUser().getEmail().equals(loggedInUserEmail)) {
                throw new UserAlreadyBusyInThisPeriodOfTimeException(loggedInUserEmail, appointment.getStartDateAndTime());
            }
        }
        createdAppointment.setActivities(new HashSet<>());
        for ( String activityName : appointmentRequestDto.getActivityNames() ) {
            createdAppointment.getActivities().add(activityRepository.findByName(activityName));
        }
        createdAppointment.setUser(loggedUser);

        appointmentRepository.save(createdAppointment);

        AppointmentResponseDto responseDto = appointmentMapper.mapAppointmentToAppointmentResponseDto(createdAppointment);
        responseDto.setActivityNames(new HashSet<>(appointmentRequestDto.getActivityNames()));
        responseDto.setUserEmail(createdAppointment.getUser().getEmail());

        return responseDto;
    }

    @Override
    public Set<AppointmentResponseDto> getAll() {
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
    public ResponseEntity<HttpStatus> delete(int appointmentId) {
        if (!appointmentRepository.findById(appointmentId).isPresent()) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public AppointmentResponseDto update(int appointmentId, AppointmentRequestDto requestDto) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        ArrayList<String> activityNames = new ArrayList<>();

        if (!appointment.isPresent()) {
            throw new AppointmentNotFoundException(appointmentId);
        }

        checkIfStartDateTimeIsNotBeforeNow(requestDto);
        LocalDateTime initialDateTime = appointment.get().getStartDateAndTime();
        appointment.get().setStartDateAndTime(requestDto.getStartDateAndTime());
        double duration = 0;
        for ( Activity activity : appointment.get().getActivities() ) {
            activityNames.add(activity.getName());
            duration += activity.getDuration();
        }
        appointment.get().setEndDateAndTime((appointment.get().getStartDateAndTime().plusHours((long) duration)));
        Set<Appointment> appointmentsBetweenGivenStartAndEndTime = appointmentRepository.findAppointmentByDateAndTimeBetween(requestDto.getStartDateAndTime(), appointment.get().getEndDateAndTime());

        for ( Appointment overlappedAppointment : appointmentsBetweenGivenStartAndEndTime ) {
            if (overlappedAppointment.getUser().getEmail().equals(appointment.get().getUser().getEmail()) && appointment.get().getId() != overlappedAppointment.getId()) {
                appointment.get().setStartDateAndTime(initialDateTime);
                appointment.get().setEndDateAndTime((appointment.get().getStartDateAndTime().minusHours((long) duration)));
                throw new UserAlreadyBusyInThisPeriodOfTimeException(appointment.get().getUser().getEmail(), overlappedAppointment.getStartDateAndTime());
            }
        }

        appointmentRepository.save(appointment.get());
        AppointmentResponseDto appointmentResponseDto = appointmentMapper.mapAppointmentToAppointmentResponseDto(appointment.get());
        appointmentResponseDto.setUserEmail(appointment.get().getUser().getEmail());
        appointmentResponseDto.setActivityNames(new HashSet<>(activityNames));
        return appointmentResponseDto;
    }

    private void checkIfStartDateTimeIsNotBeforeNow(AppointmentRequestDto requestDto) {
        if (requestDto.getStartDateAndTime().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentTimeException(requestDto.getStartDateAndTime(), LocalDateTime.now());
        }
    }
}
