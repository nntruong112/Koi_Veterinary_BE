package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.appointmentType.AppointmentTypeCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentTypeResponse;
import com.KoiHealthService.Koi.demo.entity.AppointmentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface AppointmentTypeMapper {
    AppointmentType toAppointmentType(AppointmentTypeCreationRequest request);

    @Mapping(source = "appointmentTypeId", target = "appointmentTypeId")
    AppointmentTypeResponse toAppointmentTypeResponse(AppointmentType appointmentType);
}
