package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FishRepository extends JpaRepository<Fish, String> {
    @Query("SELECT f FROM Fish f WHERE f.customer.userId = :customerId")
    List<Fish> findByCustomerId(@Param("customerId") String customerId);

}
