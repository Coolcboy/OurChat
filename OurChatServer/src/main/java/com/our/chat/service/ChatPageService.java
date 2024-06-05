package com.our.chat.service;

import com.our.chat.dto.ChatMessageOneDTO;
import com.our.chat.service.Request.ChatMessageRequest;

import java.util.List;

public interface ChatPageService {
    public List<ChatMessageOneDTO> getChatMessages(ChatMessageRequest chatMessageRequest);
}
