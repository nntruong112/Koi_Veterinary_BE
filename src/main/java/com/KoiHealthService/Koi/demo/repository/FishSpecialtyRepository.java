package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FishSpecialtyRepository extends JpaRepository<FishSpecialty, String> {
}
