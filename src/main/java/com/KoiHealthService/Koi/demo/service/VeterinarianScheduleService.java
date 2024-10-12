package com.KoiHealthService.Koi.demo.service;


import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeterinarianScheduleService {

    VeterinarianSchedule veterinarianSchedule;
    

}
