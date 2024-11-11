package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.fish.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.entity.Fish;
import lombok.NonNull;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findByAppointmentAppointmentId(String appointmentId);
}
