package com.KoiHealthService.Koi.demo.websocket;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ChatMessage {
    String content ;
    String sender;
    MessageType type;
    
}
