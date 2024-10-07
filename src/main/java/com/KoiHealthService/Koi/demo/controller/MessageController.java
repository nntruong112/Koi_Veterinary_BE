package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/chat.sendMessage") //
    @SendTo("/topic/public")
       public Message sendMessage (@Payload Message chatMessage){

        return chatMessage;
       }

    @MessageMapping("/chat.addUser") //
    @SendTo("/topic/public")
       public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor){
        //add username in websocket session
                headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
                return chatMessage;
       }
}
