package com.example.websocketdemo.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String sessionId = accessor.getSessionId();
            System.out.println("user: " + accessor.getUser().getName() + "," + "sessionId: " + sessionId);
            // 将 sessionId 与用户标识关联
//            System.out.println("sessionId: " + sessionId);
//            accessor.getUser();

        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            // 处理断开连接逻辑
            String sessionId = accessor.getSessionId();
            // 这里可以执行断开连接时的逻辑，例如清理资源、记录日志等
        }


        return message;
    }


}
