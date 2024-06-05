package com.our.chat.service;

import com.our.chat.dao.Friends;
import com.our.chat.dao.FriendsRepository;
import com.our.chat.dao.FriendsRequestRepository;
import com.our.chat.dto.FriendRequestDTO;
import com.our.chat.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FriendServiceImp implements FriendService{
    @Autowired
    private FriendsRepository friendsRepository; // 注意接口名称应与实际定义的接口名一致

    @Autowired
    private FriendsRequestRepository friendsRequestRepository;

    @Override
    public List<UserDTO> getFriends(Integer account){
        List<UserDTO> friends = friendsRepository.findFriendsByAccount(account);
        //List<UserDTO> friends = new ArrayList<>();
        if (friends == null) {
            return Collections.emptyList(); // 更好的做法是返回一个空集合而非null
        }
        return friends;
    }

    @Override
    public List<FriendRequestDTO> getFriendsNew(Integer account){
        List<FriendRequestDTO> friendsRequest = friendsRequestRepository.findFriendsRequestByAccount(account);
        if (friendsRequest == null) {
            return Collections.emptyList(); // 更好的做法是返回一个空集合而非null
        }
        return friendsRequest;
    }

    @Override
    @Transactional
    public String friends_agree(Integer sender_id, Integer receiver_id){
        int updatedRows = friendsRequestRepository.updateStatusBySenderIdAndReceiverId(sender_id, receiver_id, "agree");

        if(updatedRows > 0) {
            // 状态更新成功，可以执行其他逻辑，比如创建朋友关系等
            friendsRepository.save(new Friends(sender_id, receiver_id));
            return "OK";
        } else {
            // 如果没有找到对应的记录或更新失败，返回错误信息
            return "Failed to find or update the request";
        }
    }

}
