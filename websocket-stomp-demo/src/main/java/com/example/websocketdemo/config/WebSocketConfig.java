package com.example.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Resource
    private CustomChannelInterceptor customChannelInterceptor;
    @Resource
    private CustomHandshakeHandler customHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("http://127.0.0.1:5500")
                .setHandshakeHandler(customHandshakeHandler)
//                .setAllowedOrigins("*")
                .withSockJS().setInterceptors(new CsiHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableSimpleBroker("/topic","/queue");
        registry.enableStompBrokerRelay("/topic","/queue")
                .setUserDestinationBroadcast("/topic/log-unresolved-user")
                .setUserRegistryBroadcast("/topic/log-user-registry")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return messageConverters.add(new MappingJackson2MessageConverter());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(customChannelInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(customChannelInterceptor);
    }
}
