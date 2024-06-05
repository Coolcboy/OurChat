package com.our.chat.service;

import com.our.chat.converter.UserConverter;
import com.our.chat.dao.User;
import com.our.chat.dao.UserRepository;
import com.our.chat.dto.UserDTO;
import com.our.chat.service.Request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MeServiceImp  implements MeService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUser(Integer account) {
        User user = userRepository.findByAccount(account);
        UserDTO userDTO = UserConverter.converStudent(user);
        return userDTO;
    }
}

