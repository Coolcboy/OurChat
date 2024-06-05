package com.tian.tian.app.add_menu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.tian.tian.R;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.retrofit.RetrofitClient;
import com.tian.tian.overallModel.NetworkUtils;
import com.tian.tian.overallModel.UserDataSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFriendAdd  extends ComponentActivity {
    private ApiService apiService;
    private Button btnAddFriend;
    private EditText etAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu_friend);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        ImageView ztLeft = findViewById(R.id.ztLeft);
        ztLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前Activity
            }
        });

        btnAddFriend = findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAccount = findViewById(R.id.etFriendAccount);
                try {
                    String inputText = etAccount.getText().toString().trim();
                    Integer receiver_id = Integer.parseInt(inputText);
                    addFriendRequest(receiver_id);
                    etAccount.setText("");
                } catch (NumberFormatException e) {
                    // 处理非数字输入的情况，比如提示用户输入不合法
                    Toast.makeText(MenuFriendAdd.this, "账号请输入有效的数字！", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void addFriendRequest(Integer receiver_id) {

        //ChatMessageOne catMessageOne = new ChatMessageOne(account, oppositeAccount,content);
        //Gson gson = new Gson();
        //String jsonMessage = "{\"activity\": add_friend_send,\"receiver_id\": 7777, \"sender_id\": 123123}";
        //WebSocketClientSingleton.sendMessage(jsonMessage);

        Integer sender_id = UserDataSingleton.getInstance().getUserId();
        Call<String> call = apiService.addFriend(sender_id,receiver_id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.d("DNGH",response.body());
                if (response.isSuccessful() && response.body().equals("OK")) {
                    Toast.makeText(MenuFriendAdd.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else if(response.body().equals("isFriend")){
                    Toast.makeText(MenuFriendAdd.this, "你们已经是好基友了！", Toast.LENGTH_SHORT).show();
                }else {
                    // 处理HTTP错误
                    Toast.makeText(MenuFriendAdd.this, "账号不存在！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // 网络请求失败处理
                NetworkUtils.handleApiError(MenuFriendAdd.this,call,t);
            }
        });
    }

}
