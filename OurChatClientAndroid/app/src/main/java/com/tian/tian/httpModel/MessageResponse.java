package com.tian.tian.httpModel;

public class MessageResponse {
    private String avatar;
    private String name;
    private String last_message;
    private String time;
    private Integer oppositeAccount;

    public MessageResponse(String name, String last_message, String time,Integer oppositeAccount) {
        this.name = name;
        this.last_message = last_message;
        this.time = time;
        this.oppositeAccount = oppositeAccount;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getLast_message() {
        return last_message;
    }

    public String getTime() {
        return time;
    }

    public Integer getOppositeAccount() {
        return oppositeAccount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
