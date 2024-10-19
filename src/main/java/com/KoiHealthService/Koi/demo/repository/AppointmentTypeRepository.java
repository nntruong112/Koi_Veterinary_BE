package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, String> {
}
