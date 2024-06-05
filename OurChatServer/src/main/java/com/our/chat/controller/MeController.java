package com.our.chat.controller;

import com.our.chat.dto.UserDTO;
import com.our.chat.service.MeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class MeController {

    @Autowired
    private MeService meService;
    private static final Logger logger = LoggerFactory.getLogger(HomeMessagesController.class);

    @GetMapping("/getUserData")
    public ResponseEntity<UserDTO> message(@RequestParam("account") Integer account){
        logger.info("me请求: {}", account);
        try {
            UserDTO result = meService.getUser(account);
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
