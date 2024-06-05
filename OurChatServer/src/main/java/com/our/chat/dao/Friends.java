package com.our.chat.dao;

import jakarta.persistence.*;

@Entity
@Table(name="friends")
public class Friends {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "sender_id")
    private Integer sender_id; // 发送者ID

    @Column(name = "receiver_id")
    private Integer receiver_id; // 接收者ID

    @Column(name = "created_at")
    private String created_at; // 创建时间

    public Friends() {}
    public Friends(Integer sender_id, Integer receiver_id) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public String getId() {
        return id;
    }

    public Integer getSenderId() {
        return sender_id;
    }

    public Integer getReceiverId() {
        return receiver_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiver_id = receiverId;
    }

    public void setSenderId(Integer senderId) {
        this.sender_id = senderId;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
