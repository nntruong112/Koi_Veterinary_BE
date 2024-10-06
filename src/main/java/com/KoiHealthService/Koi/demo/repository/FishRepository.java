package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.Fish;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FishRepository extends JpaRepository<Fish, String> {
}
