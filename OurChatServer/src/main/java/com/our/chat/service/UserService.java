package com.our.chat.service;


import com.our.chat.dto.UserDTO;
import com.our.chat.service.Request.LoginRequest;
import com.our.chat.service.Request.RegisterRequest;

public interface UserService {
    public Response<UserDTO> login(LoginRequest user);

    public Response<UserDTO> register(RegisterRequest user);

}
