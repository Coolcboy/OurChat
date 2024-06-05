package com.tian.tian.overallModel;

public class UserDataSingleton {
    private static UserDataSingleton instance;
    private Integer userId;
    //private String token;

    private UserDataSingleton() {}

    public static UserDataSingleton getInstance() {
        if (instance == null) {
            synchronized (UserDataSingleton.class) {
                if (instance == null) {
                    instance = new UserDataSingleton();
                }
            }
        }
        return instance;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    // 类似的getter和setter方法也可以为token添加
}
