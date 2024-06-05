package com.our.chat.service;

import com.our.chat.dto.UserDTO;

public interface MeService {
    public UserDTO getUser(Integer account);
}
