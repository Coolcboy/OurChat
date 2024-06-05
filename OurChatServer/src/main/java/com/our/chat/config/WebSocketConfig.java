package com.our.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册WebSocket处理器，并指定路径，这里使用/userId作为路径变量
        registry.addHandler(chatWebSocketHandler, "/ws/{userId}")
                .setAllowedOrigins("*"); // 允许跨域访问，根据实际情况配置
                //.withSockJS(); // 可选，启用SockJS支持，以提供更好的兼容性和降级策略
    }
}

