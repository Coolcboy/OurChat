package com.our.chat.service;

import com.our.chat.dao.MessageRepository;
import com.our.chat.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<MessageDTO> getMessage(Integer account){
        List<MessageDTO> messages = messageRepository.findLatestMessagesByAccount(account);
        if (messages == null || messages.isEmpty()) {
            return messages;
        }
        return messages;
    }

}
