package com.our.chat.controller.add_menu;


import com.our.chat.service.menu.AddFriendService;
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
@RequestMapping("/menu")
public class AddFriendController {

    @Autowired
    private AddFriendService addFriendService;

    private static final Logger logger = LoggerFactory.getLogger(AddFriendController.class);

    @GetMapping("/addFriend")
    public ResponseEntity<String> message(@RequestParam("sender_id") Integer sender_id,@RequestParam("receiver_id") Integer receiver_id){
        String result = "";
        try {
            result = addFriendService.addFriend(sender_id,receiver_id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.info("大咩！",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (Exception e) {
            logger.info("大咩2！",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
