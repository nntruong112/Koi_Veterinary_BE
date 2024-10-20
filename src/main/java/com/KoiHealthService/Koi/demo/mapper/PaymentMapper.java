package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.PaymentRequest;
import com.KoiHealthService.Koi.demo.dto.response.PaymentResponse;
import com.KoiHealthService.Koi.demo.entity.Payment;
import org.mapstruct.Mapper;
import org.springframework.security.core.parameters.P;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toPaymentResponse(Payment payment);

    Payment toPayment(PaymentRequest paymentRequest);
}
