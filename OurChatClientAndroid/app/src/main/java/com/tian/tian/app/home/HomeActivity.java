package com.tian.tian.app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.tian.tian.R;
import com.tian.tian.app.friend.FriendFragment;
import com.tian.tian.app.me.MeFragment;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private HomeFragment homeFragment;
    private FriendFragment friendFragment;
    private MeFragment meFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        fragmentManager = getSupportFragmentManager();

        // 接收登录页面传来的账号信息
        String accountStr = getIntent().getStringExtra("account");

        //finish();
        //Log.d("NB","登录成功："+accountStr);
        Integer accountInt = Integer.parseInt(accountStr);
        // 初始化Fragments，并传递账号信息给HomeFragment
        initFragments(accountInt);

        // 获取RadioGroup并设置监听器
        RadioGroup radioGroup = findViewById(R.id.rbv);
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(this);
            // 设置默认选中的RadioButton，rb1是默认的
            radioGroup.check(R.id.rb1);
        }
    }

    private void initFragments(Integer account) {
        homeFragment = HomeFragment.newInstance(account);
        friendFragment = FriendFragment.newInstance(account);
        meFragment = MeFragment.newInstance(account);

        // 默认显示第一个Fragment
        showFragment(homeFragment);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Fragment fragment = null;

        if(checkedId==R.id.rb1){
            fragment = homeFragment;
        }
        if(checkedId==R.id.rb2){
            fragment = friendFragment;
        }
        if(checkedId==R.id.rb4){
            fragment = meFragment;
        }

        if (fragment != null) {
            showFragment(fragment);
        }
    }

    private void showFragment(Fragment fragment) {
        // 隐藏所有Fragment，避免重叠
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(R.id.fragmentContent, fragment)
                .hide(friendFragment)
                .hide(meFragment)
                .hide(homeFragment)
                .show(fragment)
                .commit();
                //.commitAllowingStateLoss();
    }
}