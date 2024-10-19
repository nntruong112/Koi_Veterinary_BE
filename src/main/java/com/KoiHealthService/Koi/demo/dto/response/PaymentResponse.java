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
    Long amountValue;
    String vnp_CreateDate;
    String vnp_ExpireDate;
    User user;

}
