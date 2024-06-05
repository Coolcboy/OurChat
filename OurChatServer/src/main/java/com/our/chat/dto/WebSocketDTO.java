package com.our.chat.dto;

public class WebSocketDTO {

    private String activity;
    private Integer sender_id;
    private Integer receiver_id;
    private String content;
    private String timestamp; // 时间戳

    public WebSocketDTO() {}
    public WebSocketDTO(String activity, Integer senderId, Integer receiverId, String content, String timestamp) {
        this.activity = activity;
        this.sender_id = senderId;
        this.receiver_id = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getActivity() {
        return activity;
    }
    public Integer getSender_id() {
        return sender_id;
    }

    public Integer getReceiver_id() {
        return receiver_id;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }
}
