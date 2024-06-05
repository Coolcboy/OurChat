package com.our.chat.dao;

import jakarta.persistence.*;

@Entity
@Table(name="chat_messages")
public class Chat_Messages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 消息ID

    @Column(name = "sender_id")
    private Integer senderId; // 发送者ID

    @Column(name = "receiver_id")
    private Integer receiverId; // 接收者ID，如果是群聊可能是群组ID

    @Column(name = "content")
    private String content; // 消息内容

    @Column(name = "timestamp")
    private String timestamp; // 时间戳
    // 其他可能的属性如消息类型、是否已读等...


    // Getter & Setter 方法
    public String getId() {
        return id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
