package com.our.chat.controller;


import com.our.chat.dto.FriendRequestDTO;
import com.our.chat.dto.UserDTO;
import com.our.chat.service.FriendService;
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
public class FriendController {
    @Autowired
    private FriendService friendService;

    private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

    @GetMapping("/friends")
    public ResponseEntity<List<UserDTO>> friends(@RequestParam("account") Integer account){
        logger.info("请求: ");
        try {
            List<UserDTO> result = friendService.getFriends(account);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) { // 更具体的异常捕获示例
            logger.error("查询失败: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/friends_new")
    public ResponseEntity<List<FriendRequestDTO>> friends_new(@RequestParam("account") Integer account){
        logger.info("请求: ");
        try {
            List<FriendRequestDTO> result = friendService.getFriendsNew(account);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) { // 更具体的异常捕获示例
            logger.error("查询失败: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/friends_agree")
    public ResponseEntity<String> friends_agree(@RequestParam("sender_id") Integer sender_id,@RequestParam("receiver_id") Integer receiver_id){
        //保存好友信息，修改好友请求列表状态。
        String result = "Failed to find or update the request";
        try {
            result = friendService.friends_agree(sender_id,receiver_id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.error("查询失败: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (Exception e) {
            logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
