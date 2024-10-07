package com.KoiHealthService.Koi.demo.entity;

import com.KoiHealthService.Koi.demo.websocket.MessageType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "messages")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String messageId;
    @ManyToOne
    @JoinColumn(name = "appointmentId")
    Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "senderId")
    User sender;
    String messageText;
    LocalDateTime timestamp;
    MessageType type;
}
