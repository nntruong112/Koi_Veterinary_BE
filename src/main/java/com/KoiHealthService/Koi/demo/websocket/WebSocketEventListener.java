package com.KoiHealthService.Koi.demo.websocket;


import com.KoiHealthService.Koi.demo.entity.Message;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j // for log
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserRepository userRepository;  // Inject UserRepository to fetch User

     //disconnect
    @EventListener
    public void HandleWebsocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String senderId = (String) headerAccessor.getSessionAttributes().get("sender").toString();
        if (senderId != null){
            log.info("user disconnected: {}", senderId);
            User sender = userRepository.findById(senderId).orElseThrow(() ->
                    new RuntimeException("User not found with id: " + senderId));
            var chatMessage = Message.builder()
                    .type(MessageType.LEAVER)
                    .sender(sender)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
