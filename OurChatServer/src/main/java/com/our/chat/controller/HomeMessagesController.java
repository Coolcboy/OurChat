package com.our.chat.controller;

import com.our.chat.dto.MessageDTO;
import com.our.chat.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class HomeMessagesController {

    @Autowired
    private MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(HomeMessagesController.class);

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> message(@RequestParam("account") Integer account){
        //logger.info("登录请求: {}", loginRequest);
        try {
            List<MessageDTO> result = messageService.getMessage(account);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) { // 更具体的异常捕获示例
            logger.error("查询失败: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
