package com.KoiHealthService.Koi.demo.dto.response;

import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    String messageId;
    Appointment appointment;
    User sender;
    String messageText;
    LocalDateTime timestamp;
}
