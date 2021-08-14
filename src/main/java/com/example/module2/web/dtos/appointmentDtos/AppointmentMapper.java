package com.example.module2.web.dtos.appointmentDtos;

import com.example.module2.entities.Appointment;
import com.example.module2.entities.User;
import com.example.module2.web.dtos.userDtos.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mappings({
            @Mapping(target="startDateAndTime", source="appointmentRequestDto.startDateAndTime"),
            @Mapping(target="endDateAndTime", source="appointmentRequestDto.endDateAndTime")
    })
    Appointment mapAppointmentDtoToAppointment(AppointmentRequestDto appointmentRequestDto);

    @Mappings({
            @Mapping(target="startDateAndTime", source="appointment.startDateAndTime"),
            @Mapping(target="endDateAndTime", source="appointment.endDateAndTime")
    })
    AppointmentResponseDto mapAppointmentToAppointmentResponseDto(Appointment appointment);

    List<AppointmentResponseDto> mapAppointmentsListToAppointmentResponseDtoList(List<Appointment> appointments);
}
