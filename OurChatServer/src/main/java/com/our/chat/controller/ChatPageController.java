package com.our.chat.controller;

import com.our.chat.dto.ChatMessageOneDTO;
import com.our.chat.service.ChatPageService;
import com.our.chat.service.Request.ChatMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatPageController {
    @Autowired
    ChatPageService chatPageService;

    @PostMapping("/getMessageOne")
    public ResponseEntity<List<ChatMessageOneDTO>> getMessageOne(@RequestBody ChatMessageRequest chatMessageRequest){
        //logger.info("me请求: {}", account);
        try {
            List<ChatMessageOneDTO> result = chatPageService.getChatMessages(chatMessageRequest);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) { // 更具体的异常捕获示例
            //logger.error("查询失败: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            //logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
