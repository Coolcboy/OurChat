package com.our.chat.service.Request;

public class LoginRequest {
    private Integer account;
    private String password;

    public Integer getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
