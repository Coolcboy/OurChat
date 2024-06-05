package com.tian.tian.httpModel;

public class LoginRequest {
    private Integer account;
    private String password;

    public LoginRequest(Integer account, String password) {
        this.account = account;
        this.password = password;
    }

    public Integer getUsername() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
