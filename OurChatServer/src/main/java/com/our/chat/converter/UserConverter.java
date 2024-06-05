package com.our.chat.converter;


import com.our.chat.dao.User;
import com.our.chat.dto.UserDTO;

public class UserConverter {
    public static UserDTO converStudent(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(user.getAccount());
        userDTO.setName(user.getName());
        userDTO.setAvatar_url(user.getAvatar_url());
        return userDTO;
    }

    public static User converStudent(UserDTO suerDTO){
        User user = new User();
        user.setAccount(suerDTO.getAccount());
        user.setName(suerDTO.getName());
        user.setAvatar_url(suerDTO.getAvatar_url());
        return user;
    }
}
