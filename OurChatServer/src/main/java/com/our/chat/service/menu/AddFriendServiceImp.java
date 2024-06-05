package com.our.chat.service.menu;

import com.our.chat.controller.LoginController;
import com.our.chat.dao.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddFriendServiceImp implements AddFriendService{

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private FriendsRequestRepository friendsRequestRepository;

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    @Transactional
    public String addFriend(Integer sender_id,Integer receiver_id){
        Boolean isFriend = friendsRepository.isFriend(sender_id, receiver_id);
        if(isFriend){
            logger.info("大咩！");
            return "isFriend";
        }
        User isReceiver = userRepository.findByAccount(receiver_id);

        if(isReceiver==null){
            logger.info("不存在！");
            return "Not found";
        }
        friendsRequestRepository.save(new FriendsRequest(sender_id,receiver_id));
        logger.info("好友请求保存成功！");

        return "OK";
    }
}
