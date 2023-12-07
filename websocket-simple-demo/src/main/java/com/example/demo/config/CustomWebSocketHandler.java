package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> sessionRegistry = new ConcurrentHashMap<>();
    private LinkedList<String> sessionIdPool = new LinkedList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 在这里处理收到的消息
        System.out.println("Received message: " + message.getPayload());

        // 示例：向客户端发送一个响应消息
        session.sendMessage(new TextMessage("Hello, client!"));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established with session ID: " + session.getId());
        sessionRegistry.putIfAbsent(session.getId(), session);
        //only demo have concurrency issue here
        sessionIdPool.add(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed with session ID: " + session.getId());
        sessionRegistry.remove(session.getId());
        sessionIdPool.remove(session.getId());
    }

    public void broadcast(String message) {
        for (WebSocketSession session : sessionRegistry.values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    // 异常处理逻辑
                }
            }
        }
    }

    public Map<String, WebSocketSession> getSessionRegistry() {
        return sessionRegistry;
    }

    public LinkedList<String> getSessionIdPool() {
        return sessionIdPool;
    }
}
