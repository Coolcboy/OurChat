package com.our.chat.service;

import com.our.chat.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    public List<MessageDTO> getMessage(Integer account);
}
