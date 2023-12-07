package com.example.websocketdemo.config;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener implements ApplicationListener<AbstractSubProtocolEvent> {

    private Map<String, String> sessionRegistry = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(AbstractSubProtocolEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (event instanceof SessionConnectedEvent) {
            System.out.println("WebSocket Connection Established: " + headerAccessor.getSessionId() + ", user info: " +headerAccessor.getUser());
        } else if (event instanceof SessionDisconnectEvent) {
            System.out.println("WebSocket Connection Closed: " + headerAccessor.getSessionId());
        }
    }
}
