package com.example.websocketdemo.controller;

import com.example.websocketdemo.vo.SampleObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class CsiWebSocketController {

    @MessageMapping("/helloToAll")
    @SendTo("/topic/greetings")
    public SampleObject processMessage(@Payload SampleObject message) {
        System.out.println(message.getName());
        return message;
    }

    @MessageMapping("/helloToMe")
    @SendToUser("/queue/notifications")
    public SampleObject processMessage2(@Payload SampleObject message) {
//        System.out.println(principal.getName());
        System.out.println(message.getName());
        return message;
    }

}
