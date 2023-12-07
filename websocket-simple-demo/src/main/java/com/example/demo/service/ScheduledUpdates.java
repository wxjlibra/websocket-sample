package com.example.demo.service;

import com.example.demo.config.CustomWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;

@Component
public class ScheduledUpdates {

    @Resource
    private CustomWebSocketHandler customWebSocketHandler;

    @Scheduled(fixedRate = 5000)
    public void mimicTaskDoneMessage() throws IOException {
        customWebSocketHandler.broadcast("hello announcement comes");
        if (customWebSocketHandler.getSessionIdPool().size() > 0) {
            int tmp = new Random().nextInt(customWebSocketHandler.getSessionIdPool().size());
            String sessionKey = customWebSocketHandler.getSessionIdPool().get(tmp);
            customWebSocketHandler.getSessionRegistry().get(sessionKey).sendMessage(new TextMessage("this is a message for you"));
        }
    }

    public static void main(String[] args) {
        int tmp = new Random().nextInt(4);
        System.out.println(tmp);
    }

}
