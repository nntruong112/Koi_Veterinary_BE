package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    @Query("SELECT a FROM Appointment a WHERE a.customer.userId = :customerId")
    List<Appointment> findAppointmentsByCustomerId(@Param("customerId") String customerId);

    @Query("SELECT a FROM Appointment a WHERE a.veterinarian.userId = :veterinarianId") //lưu ý, đặt tên ID phải giống param  
    List<Appointment> findAppointmentsByVeterinarianId(@Param("veterinarianId") String vetId);


    List<Appointment> findByPaymentStatus(String paymentStatus);

    @Query("SELECT a.fish FROM Appointment a WHERE a.appointmentId = :appointmentId")
    Fish findFishByAppointmentId(@Param("appointmentId") String appointmentId);

}
