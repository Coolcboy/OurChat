package com.our.chat.dto;

public class FriendRequestDTO {
    private Integer account;
    private String name;
    private String avatar_url;
    private String status;

    public FriendRequestDTO(){}

    public FriendRequestDTO(Integer account, String name, String avatar_url, String status) {
        this.account = account;
        this.name = name;
        this.avatar_url = avatar_url;
        this.status = status;
    }

    public Integer getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getStatus() {
        return status;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
