package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.entity.VeterinarianProfile;
import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarianProfileRepository extends JpaRepository<VeterinarianProfile, String> {
    List<VeterinarianProfile> findByUser(User user);
    boolean existsByUserAndVeterinarianSchedule(User user, VeterinarianSchedule veterinarianSchedule);
}
