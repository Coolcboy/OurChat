package com.tian.tian;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class MyApp extends Application {

    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 这里可以做一些全局的初始化工作
    }

    public static MyApp getInstance() {
        return instance;
    }

    public boolean isLogin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("IS_LOGGED_IN", false);
    }

    public String getUserId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("USER_ID", ""); // 用户ID是以Integer形式存储的
    }


}
