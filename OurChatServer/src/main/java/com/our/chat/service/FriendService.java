package com.our.chat.service;


import com.our.chat.dto.FriendRequestDTO;
import com.our.chat.dto.UserDTO;

import java.util.List;

public interface FriendService {
    public List<UserDTO> getFriends(Integer account);
    public List<FriendRequestDTO> getFriendsNew(Integer account);
    public String friends_agree(Integer sender_id, Integer receiver_id);
}
