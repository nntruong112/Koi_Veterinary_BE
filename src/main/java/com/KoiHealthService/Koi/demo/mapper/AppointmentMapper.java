package com.KoiHealthService.Koi.demo.mapper;


import com.KoiHealthService.Koi.demo.dto.request.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import lombok.NonNull;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @NonNull
    Appointment toAppointment(AppointmentRequest request);
    @NonNull
    AppointmentResponse toAppointmentResponse(Appointment appointment);
}
