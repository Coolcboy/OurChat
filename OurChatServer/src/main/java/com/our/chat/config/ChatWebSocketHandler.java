package com.our.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.our.chat.config.ChatService;
import com.our.chat.dto.ChatMessageOneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;

    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private static int onlineCount = 0;

    // 保存所有连接的WebSocketSession，可以根据需要调整存储结构
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Autowired
    public ChatWebSocketHandler(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立后可以做一些初始化操作，比如添加session到集合中
        String userId = getUserId(session);
        sessions.put(userId, session);
        OnlineCountChange(1);
        logger.info("WebSocket连接已建立: {}", userId);
        logger.info("当前在线人数: {}", getOnlineCount());

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理接收到的文本消息
        String payload = message.getPayload();
        logger.info(payload);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChatMessageOneDTO chatMessageOneDTO = objectMapper.readValue(payload, ChatMessageOneDTO.class);
            String receiverId = chatMessageOneDTO.getReceiver_id().toString();
            String context = chatMessageOneDTO.getContent();

            chatService.saveMessage(chatMessageOneDTO); // 保存消息到数据库

            WebSocketSession receiverSession = sessions.get(receiverId);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(context));
            }

        }catch (IOException e){
            logger.error("JSON解析错误:", e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 连接关闭时的操作，如清理session
        String userId = getUserId(session);
        sessions.remove(userId);
        OnlineCountChange(-1);
        logger.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    public String getUserId(WebSocketSession session){
        URI url = session.getUri();
        String path = url.getPath(); // 获取路径部分
        if (path.startsWith("/ws/")) {
            String userId = path.substring(4); // 截取"/ws/"之后的部分作为userId
            return userId;
        } else {
            System.err.println("路径不符合预期，无法提取userId");
        }
        return "0";
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void OnlineCountChange(int num) {
        ChatWebSocketHandler.onlineCount+=num;
    }

}