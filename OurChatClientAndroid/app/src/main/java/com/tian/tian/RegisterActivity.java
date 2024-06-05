package com.tian.tian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.tian.tian.app.home.HomeActivity;
import com.tian.tian.httpModel.LoginResponse;
import com.tian.tian.httpModel.RegisterRequest;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.retrofit.RetrofitClient;
import com.tian.tian.overallModel.NetworkUtils;
import com.tian.tian.overallModel.UserDataSingleton;
import com.tian.tian.overallModel.WebSocketClientSingleton;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends ComponentActivity {

    private ApiService apiService;
    private UserDataSingleton userDataSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_register_activity);

        userDataSingleton = UserDataSingleton.getInstance();//全局userId

        // 获取按钮的引用并设置点击监听器
        Button btnLogin = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户名和密码的输入值
                EditText etAccount = findViewById(R.id.etAccount);
                EditText etUsername = findViewById(R.id.etUsername);
                EditText etPassword = findViewById(R.id.etPassword);
                try {
                    String inputText = etAccount.getText().toString().trim();
                    Integer account = Integer.parseInt(inputText);
                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    // 调用登录请求函数
                    sendLoginRequest(account,username ,password);
                } catch (NumberFormatException e) {
                    // 处理非数字输入的情况，比如提示用户输入不合法
                    Toast.makeText(RegisterActivity.this, "账号请输入有效的数字！", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void sendLoginRequest(Integer account, String username,String password) {

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //ApiService apiService = retrofit.create(ApiService.class);

        RegisterRequest registerRequest = new RegisterRequest(account,username ,password);

        Call<LoginResponse> call = apiService.register(registerRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        // 登录成功处理
                        //saveLoginStatus(LoginActivity.this, response.body().getData().getAccount(), response.body().getToken());
                        //建立Websocket连接
                        WebSocketClientSingleton.getInstance("ws://10.115.2.150:8080/ws/"+account.toString());

                        //登录成功后跳转到主页面HomeActivity
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        Integer account = response.body().getData().getAccount();
                        userDataSingleton.setUserId(account);
                        intent.putExtra("account", account.toString());//传数据
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        Log.d("NB","注册成功");
                        finish();
                    } else {
                        // 显示错误消息
                        //Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, "账号已存在！", Toast.LENGTH_SHORT).show();
                        Log.d("NB","炸了");
                    }
                } else {
                    // 处理HTTP错误
                    Toast.makeText(RegisterActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // 网络请求失败处理
                NetworkUtils.handleApiError(RegisterActivity.this,call,t);
            }
        });
    }

    //缓存token
    private void saveLoginStatus(Context context, Integer userId, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("USER_ID", userId);
        editor.putString("TOKEN", token);
        editor.putBoolean("IS_LOGGED_IN", true);
        editor.apply();
    }

    public void loginNowClicked(View view) {
        // 在这里处理点击事件，比如启动注册Activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
