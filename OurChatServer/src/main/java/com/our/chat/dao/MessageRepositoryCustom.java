package com.our.chat.dao;

import com.our.chat.dto.ChatMessageOneDTO;
import com.our.chat.dto.MessageDTO;
import com.our.chat.service.Request.ChatMessageRequest;

import java.util.List;

public interface MessageRepositoryCustom {
    List<MessageDTO> findLatestMessagesByAccount(Integer account);

    List<ChatMessageOneDTO> findChatMessageByRequest(ChatMessageRequest chatMessageRequest);
}
