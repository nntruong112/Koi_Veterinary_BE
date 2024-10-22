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
    @Query("SELECT v FROM VeterinarianSchedule v JOIN v.veterinarianProfiles vp WHERE vp.user.userId = :veterinarianId")
    List<VeterinarianSchedule> findScheduleByVeterinarianId(@Param("veterinarianId") String veterinarianId);

    
}
