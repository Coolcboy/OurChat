package com.tian.tian.httpModel;

public class ChatMessageOne {
    private Integer sender_id;
    private Integer receiver_id;
    private String content;
    private String timestamp; // 时间戳


    public ChatMessageOne(Integer sender_id,Integer receiver_id){
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content = content;
    }

    public ChatMessageOne(Integer sender_id,Integer receiver_id,String content){
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content = content;
    }


    public Integer getSenderId() {
        return sender_id;
    }

    public Integer getReceiverId() {
        return receiver_id;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
