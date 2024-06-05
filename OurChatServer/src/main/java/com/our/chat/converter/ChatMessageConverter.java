package com.our.chat.converter;

import com.our.chat.dao.Chat_Messages;
import com.our.chat.dto.ChatMessageOneDTO;

public class ChatMessageConverter {
    public static Chat_Messages convertEntity(ChatMessageOneDTO chatMessageOneDTO) {
        Chat_Messages chatMessage = new Chat_Messages();
        chatMessage.setSenderId(chatMessageOneDTO.getSender_id());
        chatMessage.setReceiverId(chatMessageOneDTO.getReceiver_id());
        chatMessage.setContent(chatMessageOneDTO.getContent());
        return chatMessage;
    }

    public static ChatMessageOneDTO convertDTO(Chat_Messages chatMessage) {
        ChatMessageOneDTO chatMessageOneDTO = new ChatMessageOneDTO();
        chatMessageOneDTO.setSender_id(chatMessage.getSenderId());
        chatMessageOneDTO.setReceiver_id(chatMessage.getReceiverId());
        chatMessageOneDTO.setContent(chatMessage.getContent());
        return chatMessageOneDTO;
    }

}
