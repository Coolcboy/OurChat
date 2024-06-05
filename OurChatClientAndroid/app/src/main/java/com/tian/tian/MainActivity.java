package com.tian.tian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import androidx.activity.ComponentActivity;

import com.tian.tian.app.home.HomeActivity;
import com.tian.tian.cache.Cache;

public class MainActivity extends ComponentActivity {

    private boolean isAnimEnd = false;
    private boolean isRequest = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // 加载并启动动画
        View view = findViewById(android.R.id.content);
        startAnimation(view);
    }

    private void startAnimation(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_in);
        anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时的操作，这里可以留空
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimEnd = true;
                startActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复时的操作，这里可以留空
            }
        });
        view.startAnimation(anim);
    }

    private void startActivity() {
        if (isAnimEnd && isRequest) {
            MyApp app = (MyApp) getApplication(); // MyApp是应用类，需要实现isLogin方法
            if (app.isLogin()) {
                //HeartBeatService.startHeartBeatService(this);
                startHomeActivity(); // 跳转到主页面的方法
            } else {
                String username = new Cache(this).getUsername();
                startLoginActivity(username);
            }
            finish();
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void startLoginActivity(String username) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
