package com.tian.tian.httpModel;

public class FriendData {
    private Integer account;//账号
    private String name; // 显示的名字
    private String avatar_url; // 头像的URL
    private String status; // 状态，比如在线、离线等

    // 可能还有其他属性...

    // 构造方法
    public FriendData(int account,String showName, String avatarUrl, String status) {
        this.account = account;
        this.name = showName;
        this.avatar_url = avatarUrl;
        this.status = status;
    }

    // Getter和Setter方法
    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getShowName() {
        return name;
    }

    public void setShowName(String showName) {
        this.name = showName;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
