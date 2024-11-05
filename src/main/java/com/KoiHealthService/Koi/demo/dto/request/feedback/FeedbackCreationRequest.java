package com.KoiHealthService.Koi.demo.dto.request.feedback;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackCreationRequest {
    String feedbackId;
    String comment;
    Integer rating;
    String customerId;
    String appointmentId;
    String punctuality;
}
