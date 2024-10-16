package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NonNull
public interface VeterinarianScheduleRepository extends JpaRepository<VeterinarianSchedule, String> {
    @Query("SELECT vs FROM VeterinarianSchedule vs JOIN vs.veterinarians v WHERE v.userId = :veterinarianId")
    List<VeterinarianSchedule> findScheduleByVeterinarianId(@Param("veterinarianId") String veterinarianId);
}
