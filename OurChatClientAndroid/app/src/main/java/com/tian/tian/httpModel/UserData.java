package com.tian.tian.httpModel;

public class UserData {
    private Integer account;
    //private String password;
    private String name;
    private String avatar_url;

    // Getter 和 Setter 方法
    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
