package com.our.chat.dto;

public class MessageDTO {

    private String avatar;
    private String name;
    private Integer oppositeAccount;
    private String last_message;
    private String time;

    public MessageDTO (){}

    public MessageDTO(String avatar, String name, Integer oppositeAccount, String last_message, String time) {
        this.avatar = avatar;
        this.name = name;
        this.oppositeAccount = oppositeAccount;
        this.last_message = last_message;
        this.time = time;
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


}
