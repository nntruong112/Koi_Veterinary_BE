package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@NonNull
public interface VeterinarianScheduleRepository extends JpaRepository<VeterinarianSchedule, String> {
}
