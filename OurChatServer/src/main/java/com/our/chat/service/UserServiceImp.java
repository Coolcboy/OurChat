package com.our.chat.service;

import com.our.chat.converter.UserConverter;
import com.our.chat.dao.User;
import com.our.chat.dao.UserRepository;
import com.our.chat.dto.UserDTO;
import com.our.chat.service.Request.LoginRequest;
import com.our.chat.service.Request.RegisterRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String SECRET_KEY_STRING = "your-very-secret-key-here-which-should-be-long-and-random";

//    @Autowired
//    private PasswordEncoder passwordEncoder; // 直接注入PasswordEncoder

    @Override
    public Response<UserDTO> login(LoginRequest LoginRequest) {
        User user = userRepository.findByAccount(LoginRequest.getAccount());
        if (user == null || !(LoginRequest.getPassword().equals(user.getPassword()))) {
            return Response.newFail("登录失败，密码错误");
        }
        // 登录成功，生成JWT Token
        Map<String, Object> claims = new HashMap<>();
        //claims.put("userId", user.getId()); // 可以根据需要添加更多的claim
        String token = generateJwtToken(claims, user.getName());
        // 将User转换为UserDTO，并设置token
        UserDTO userDTO = UserConverter.converStudent(user);
        userDTO.setToken(token);

        return Response.<UserDTO>newSuccess(userDTO);
    }

    //生成token
    private String generateJwtToken(Map<String, Object> claims, String subject) {
        String encodedKey = Base64.getEncoder().encodeToString(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 有效期1天
                .signWith(SignatureAlgorithm.HS256,encodedKey )
                .compact();
    }


    @Override
    public Response<UserDTO> register(RegisterRequest registerRequest) {
        User user = userRepository.findByAccount(registerRequest.getAccount());
        if (user != null) {
            return Response.newFail("账号已存在");
        }
        User user_r = new User();
        user_r.setAccount(registerRequest.getAccount());
        user_r.setPassword(registerRequest.getPassword());
        user_r.setName(registerRequest.getName());
        user_r.setAvatar_url("http://example.com/avatar_default.jpg");
        userRepository.save(user_r);
        Map<String, Object> claims = new HashMap<>();
        String token = generateJwtToken(claims, user_r.getName());
        UserDTO userDTO = UserConverter.converStudent(user_r);
        userDTO.setToken(token);
        return Response.<UserDTO>newSuccess(userDTO);
    }


}
