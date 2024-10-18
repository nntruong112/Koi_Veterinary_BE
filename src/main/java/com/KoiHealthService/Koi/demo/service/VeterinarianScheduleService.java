package com.KoiHealthService.Koi.demo.service;


import com.KoiHealthService.Koi.demo.dto.request.VeterinarianScheduleRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.dto.response.VeterinarianScheduleResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
import com.KoiHealthService.Koi.demo.mapper.VeterinarianScheduleMapper;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.FishSpecialtyRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import com.KoiHealthService.Koi.demo.repository.VeterinarianScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VeterinarianScheduleService {


    VeterinarianScheduleRepository veterinarianScheduleRepository;

    FishSpecialtyRepository fishSpecialtyRepository;

    UserRepository userRepository;
    
    VeterinarianScheduleMapper veterinarianScheduleMapper;

    UserMapper userMapper;


    public VeterinarianSchedule createVeterinarianSchedule(@Valid VeterinarianScheduleRequest request) {
        // Fetch the veterinarian
        User veterinarian = userRepository.findById(request.getVeterinarianId()).orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));


        // Build the VeterinarianSchedule entity
        VeterinarianSchedule veterinarianSchedule = VeterinarianSchedule.builder()
                .scheduleId(request.getScheduleId())
                .availableDate(request.getAvailableDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        // Save and return the schedule
        return veterinarianScheduleRepository.save(veterinarianSchedule);
    }

    public List<VeterinarianSchedule> getAllVeterinarianSchedules() {
        return veterinarianScheduleRepository.findAll();
    }


    //get schedule by id ===============================================================================
    public VeterinarianSchedule getScheduleById(String id) {
        VeterinarianSchedule veterinarianSchedule = veterinarianScheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish is not found") );

       // List<User> veterinarian = veterinarianSchedule.getVeterinarians();

        return VeterinarianSchedule.builder()
                .availableDate(veterinarianSchedule.getAvailableDate())
                .startTime(veterinarianSchedule.getStartTime())
                .endTime(veterinarianSchedule.getEndTime())
                .scheduleId(veterinarianSchedule.getScheduleId())
                .build();

    }

//    public List<VeterinarianScheduleResponse> getScheduleByVeterinarianId(String veterinarianId) {
//        User veterinarian = userRepository.findById(veterinarianId).orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));
//        List<VeterinarianSchedule> schedules = veterinarianScheduleRepository.findScheduleByVeterinarianId(veterinarianId);
//        return schedules.stream()
//                .map(veterinarianScheduleMapper::toVeterinarianResponse)
//                .collect(Collectors.toList());
//    }

    

}
