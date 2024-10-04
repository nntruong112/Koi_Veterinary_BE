package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, String> {
}
