package com.our.chat.dao;

import jakarta.persistence.*;

@Entity
@Table(name="friends_request")
public class FriendsRequest {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "sender_id")
    private Integer sender_id; // 发送者ID

    @Column(name = "receiver_id")
    private Integer receiver_id; // 接收者ID

    @Column(name = "status")
    private String status;

    public FriendsRequest() {}
    public FriendsRequest(Integer sender_id, Integer receiver_id) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.status = "default";
    }

    public String getId() {
        return id;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public Integer getReceiver_id() {
        return receiver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
