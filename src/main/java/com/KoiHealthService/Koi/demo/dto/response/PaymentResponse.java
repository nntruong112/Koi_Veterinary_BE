package com.KoiHealthService.Koi.demo.dto.response;

import com.KoiHealthService.Koi.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String message;
    String paymentUrl;
    String paymentId;
    String orderType;
    String userId;
    String email;
    String payDate;
    Long amountValue;
    String username;
}
