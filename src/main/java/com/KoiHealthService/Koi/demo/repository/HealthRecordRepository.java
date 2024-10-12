package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.dto.response.HealthRecordResponse;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, String> {

    @Query("SELECT h FROM HealthRecord h WHERE h.fish.fishId = :fishId")
    List<HealthRecord> findByFishId (@Param("fishId") String fishId);
}
