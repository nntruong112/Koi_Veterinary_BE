package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment,String> {

    @Query("SELECT SUM(p.amountValue) FROM Payment p WHERE MONTH(p.vnp_PayDate) = :month")
    Long getAmountValueInMonth(@Param("month") int month);
}
