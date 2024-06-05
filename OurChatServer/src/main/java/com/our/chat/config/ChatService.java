package com.our.chat.config;

import com.our.chat.controller.LoginController;
import com.our.chat.converter.ChatMessageConverter;
import com.our.chat.dao.Chat_Messages;
import com.our.chat.dao.MessageRepository;
import com.our.chat.dto.ChatMessageOneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);


    public void saveMessage(ChatMessageOneDTO chatMessageOneDTO) {
        Chat_Messages chat_Messages = ChatMessageConverter.convertEntity(chatMessageOneDTO);
        messageRepository.save(chat_Messages);
        logger.info("消息保存成功！");
    }
}
