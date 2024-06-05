package com.tian.tian.app.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tian.tian.LoginActivity;
import com.tian.tian.MainActivity;
import com.tian.tian.R;
import com.tian.tian.overallModel.NetworkUtils;
import com.tian.tian.overallModel.WebSocketClientSingleton;

public class MePageSet extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_page_set);

        ImageView ztLeft = findViewById(R.id.ztLeft);
        ztLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前Activity
            }
        });

        TextView ztSwitch = findViewById(R.id.ztSwitch);
        ztSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NetworkUtils.functionDeveloping(MePageSet.this);
            }
        });

        TextView ztLogout = findViewById(R.id.ztLogout);
        ztLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                showLogoutOptions();
            }
        });
    }

    private void showLogoutOptions() {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.me_bottom_dialog, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    public void onLogoutClick(View view) {
        // 处理退出登录逻辑
        Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
        // 断开WebSocket连接
        WebSocketClientSingleton.closeConnection();
        // 正确关闭对话框
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(MePageSet.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onCloseAppClick(View view) {
        // 处理关闭应用逻辑
        Toast.makeText(this, "关闭微信", Toast.LENGTH_SHORT).show();
        // 正确关闭对话框
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onCancelClick(View view) {
        // 取消操作，关闭对话框
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

}
