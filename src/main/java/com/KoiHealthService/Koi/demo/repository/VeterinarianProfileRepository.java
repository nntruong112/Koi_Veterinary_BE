package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.entity.VeterinarianProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarianProfileRepository extends JpaRepository<VeterinarianProfile, String> {
    List<VeterinarianProfile> findByUser(User user);
}
