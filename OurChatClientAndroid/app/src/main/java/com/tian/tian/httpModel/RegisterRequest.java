package com.tian.tian.httpModel;

public class RegisterRequest {
    public Integer account;
    public String password;
    public String username;

    public RegisterRequest(Integer account,String username ,String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }

}
