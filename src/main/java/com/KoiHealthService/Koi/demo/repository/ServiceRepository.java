package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.Service;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ServiceRepository extends JpaRepository<Service, String> {
}
