package com.tian.tian.app.chat;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tian.tian.R;
import com.tian.tian.app.adapter.ChatAdapter;
import com.tian.tian.httpModel.ChatMessageOne;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.WebSocketService.MessageUpdateListener;
import com.tian.tian.httpService.retrofit.RetrofitClient;
import com.tian.tian.overallModel.NetworkUtils;
import com.tian.tian.overallModel.UserDataSingleton;
import com.tian.tian.overallModel.WebSocketClientSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends ComponentActivity implements MessageUpdateListener {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessageOne> chatMessages;
    private EditText etContent;
    private TextView tvSend;
    private ImageView ztSound, ztEmoji,ztLeft,ztRight;
    private SwipeRefreshLayout srl;
    private Integer userId;
    private Integer account,oppositeAccount;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        userId = UserDataSingleton.getInstance().getUserId();
        //Log.d("Fuck see this","MK:"+userId);

        String accountStr = getIntent().getStringExtra("account");
        account = Integer.parseInt(accountStr);
        String oppositeAccountStr = getIntent().getStringExtra("oppositeAccount");
        oppositeAccount = Integer.parseInt(oppositeAccountStr);
        String oppositeName = getIntent().getStringExtra("oppositeName");
        //设置顶部栏名字
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(oppositeName);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        loadInitialChatMessages();

        //监听websocket
        WebSocketClientSingleton.addListener(this);

        // 初始化视图
        recyclerView = findViewById(R.id.rv);
        etContent = findViewById(R.id.etContent);
        tvSend = findViewById(R.id.tvSend);
        ztSound = findViewById(R.id.ztSound);
        ztEmoji = findViewById(R.id.ztEmoji);
        srl = findViewById(R.id.srl);

        ztLeft = findViewById(R.id.ztLeft);
        ztRight = findViewById(R.id.ztRight);

        // 设置布局管理器和适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatMessages = new ArrayList<>();
        //Log.d("真相只有一个","你才是那个凶手："+userId);
        chatAdapter = new ChatAdapter(this, chatMessages, userId);
        recyclerView.setAdapter(chatAdapter);

        // 设置SwipeRefreshLayout的刷新监听
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 在这里执行刷新聊天记录的逻辑，例如从服务器获取最新消息
                // 刷新完成后调用 srl.setRefreshing(false);
                srl.setRefreshing(true);
                srl.setRefreshing(false);
            }
        });

        // 发送消息按钮点击事件
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString().trim();
                if (!content.isEmpty()) {
                    Log.d("Fuck see this","Body");
                    // 这里应该添加发送消息到服务器的逻辑
                    ChatMessageOne catMessageOne = new ChatMessageOne(account, oppositeAccount,content);
                    Gson gson = new Gson();
                    String jsonMessage = gson.toJson(catMessageOne);
                    WebSocketClientSingleton.sendMessage(jsonMessage);
                    //更新消息列表
                    MessageSingleUpdata(catMessageOne);
                    etContent.setText("");//清除输入框
                    recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);//滚动到底部
                }
            }
        });

        ztLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        ztRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NetworkUtils.functionDeveloping(ChatActivity.this);
            }
        });

        //语音输入
        ztSound.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NetworkUtils.functionDeveloping(ChatActivity.this);
            }
        });
        //表情包
        ztEmoji.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NetworkUtils.functionDeveloping(ChatActivity.this);
            }
        });

    }

    //销毁时
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除监听器以防止内存泄漏
        WebSocketClientSingleton.removeListener(this);
    }

    //收到聊天信息更新
    @Override
    public void onMessageUpdate(String message) {
        // 这里会接收到WebSocket的新消息，可以用来更新UI
        ChatMessageOne chatMessageOne = new ChatMessageOne(oppositeAccount,userId,message);
        runOnUiThread(() -> MessageSingleUpdata(chatMessageOne));
    }


    //初次请求
    private void loadInitialChatMessages() {
        ChatMessageOne catMessageOne = new ChatMessageOne(account, oppositeAccount);

        Call<List<ChatMessageOne>> call = apiService.getMessageOne(catMessageOne);

        call.enqueue(new Callback<List<ChatMessageOne>>() {
            @Override
            public void onResponse(Call<List<ChatMessageOne>> call, Response<List<ChatMessageOne>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("NB","列表请求成功");
                        List<ChatMessageOne> newMessages = response.body();
                        // 将新获取的消息添加到列表末尾，并通知Adapter数据已更改
                        MessageMultipleUpdata(newMessages);
                    } else {
                        // 显示错误消息
                        Toast.makeText(ChatActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                        Log.d("NB","炸了");
                    }
                } else {
                    // 处理HTTP错误
                    Toast.makeText(ChatActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChatMessageOne>> call, Throwable t) {
                // 网络请求失败处理
                NetworkUtils.handleApiError(ChatActivity.this, call, t);
            }
        });
    }

    //更新多条消息
    private void MessageMultipleUpdata(List<ChatMessageOne> newMessages){
        //chatMessages.addAll(chatMessages.size(), newMessages);
        //int startIndex = chatMessages.size() - newMessages.size();
        //int endIndex = chatMessages.size();
        //chatAdapter.notifyItemRangeInserted(startIndex, endIndex - startIndex);

//        chatMessages.addAll(0, newMessages);
//        int newSize = newMessages.size();
//        chatAdapter.notifyItemRangeInserted(0, newSize);
        Collections.sort(newMessages, Comparator.comparing(m -> m.getTimestamp()));
        chatMessages.addAll(newMessages);
        chatAdapter.notifyDataSetChanged();

    }

    //更新单条消息
    private void MessageSingleUpdata(ChatMessageOne newMessages){
        chatMessages.add(chatMessages.size(), newMessages);
        int startIndex = chatMessages.size() - 1;
        int endIndex = chatMessages.size();
        chatAdapter.notifyItemRangeInserted(startIndex, endIndex - startIndex);
    }


}
