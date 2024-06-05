package com.our.chat.controller;

import com.our.chat.dao.User;
import com.our.chat.dto.UserDTO;
import com.our.chat.service.Request.LoginRequest;
import com.our.chat.service.Request.RegisterRequest;
import com.our.chat.service.Response;
import com.our.chat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class LoginController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/student")
    public Response<String> getStudent(){
        return Response.newSuccess("ok");
    }

    @PostMapping("/login")
    public ResponseEntity<Response<UserDTO>> login(@RequestBody LoginRequest loginRequest){
//         打印接收到的登录请求信息到控制台
        logger.info("登录请求: {}", loginRequest);

        try {
            Response<UserDTO> result = userService.login(loginRequest);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) { // 更具体的异常捕获示例
            logger.error("登陆失败密码不对: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.newFail("登录失败"));
        } catch (Exception e) {
            logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.newFail("服务器错误"));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody RegisterRequest registerRequest){
        //打印接收到的登录请求信息到控制台
        logger.info("注册请求: {}", registerRequest);

        try {
            Response<UserDTO> result = userService.register(registerRequest);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) { // 更具体的异常捕获示例
            logger.error("注册失败: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.newFail("登录失败"));
        } catch (Exception e) {
            logger.error("其他问题: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.newFail("服务器错误"));
        }

    }

}
