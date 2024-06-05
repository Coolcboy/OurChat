package com.tian.tian.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class Cache {

    private static final String PREF_NAME = "UserPreferences";
    private static final String KEY_USERNAME = "lastLoggedInUser";

    private Context context;

    public Cache(Context context) {
        this.context = context;
    }

    public void setUsername(String username) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(KEY_USERNAME, "");
    }
}
