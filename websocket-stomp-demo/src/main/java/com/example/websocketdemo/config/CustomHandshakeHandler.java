package com.example.websocketdemo.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    int seq = 0;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = UUID.randomUUID().toString();
//        String userId = "mockUserId2";
        return new StompPrincipal(userId);
    }
}
