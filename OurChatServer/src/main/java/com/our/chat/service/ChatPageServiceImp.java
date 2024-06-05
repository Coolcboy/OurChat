package com.our.chat.service;

import com.our.chat.dao.MessageRepository;
import com.our.chat.dto.ChatMessageOneDTO;
import com.our.chat.dto.MessageDTO;
import com.our.chat.service.Request.ChatMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatPageServiceImp implements ChatPageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<ChatMessageOneDTO> getChatMessages(ChatMessageRequest chatMessageRequest) {
        List<ChatMessageOneDTO> messages = messageRepository.findChatMessageByRequest(chatMessageRequest);
        return messages;
    }

}
