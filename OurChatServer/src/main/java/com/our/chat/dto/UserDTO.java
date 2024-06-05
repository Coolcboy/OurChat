package com.our.chat.dto;

public class UserDTO {
    private Integer account;
    private String name;
    private String avatar_url;
    private String token;

    public UserDTO(){}

    public UserDTO(Integer account, String name, String avatar_url) {
        this.account = account;
        this.name = name;
        this.avatar_url = avatar_url;
    }


    public Integer getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }
    public String getAvatar_url() {return avatar_url;}

    public String getToken() {return token;}


    public void setAccount(Integer account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {this.token = token;}

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
