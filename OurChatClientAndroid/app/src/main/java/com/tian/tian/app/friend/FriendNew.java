package com.tian.tian.app.friend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tian.tian.R;
import com.tian.tian.app.adapter.friend_page.FriendNewAdapter;
import com.tian.tian.httpModel.FriendData;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.retrofit.RetrofitClient;
import com.tian.tian.overallModel.NetworkUtils;
import com.tian.tian.overallModel.UserDataSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendNew  extends ComponentActivity {

    private RecyclerView recyclerView;
    private FriendNewAdapter friendNewAdapter;
    private List<FriendData> friendDataList;
    private SwipeRefreshLayout srl;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_new_page);

        Integer userId = UserDataSingleton.getInstance().getUserId();
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        loadFriendsList(userId);

        recyclerView = findViewById(R.id.rv);
        srl = findViewById(R.id.srl);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendDataList = new ArrayList<>();
        friendNewAdapter = new FriendNewAdapter(this, friendDataList, userId, this::onFriendItemClick);
        recyclerView.setAdapter(friendNewAdapter);

        //下拉刷新
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 在这里执行刷新聊天记录的逻辑，例如从服务器获取最新消息
                // 刷新完成后调用 srl.setRefreshing(false);
                loadFriendsList(userId);
                srl.setRefreshing(true);
                //srl.setRefreshing(false);
            }
        });


        ImageView ztLeft = findViewById(R.id.ztLeft);
        ztLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前Activity
            }
        });

    }

    protected void loadFriendsList(Integer userId){

        apiService.getFriendsNew(userId).enqueue(new Callback<List<FriendData>>() {
            @Override
            public void onResponse(Call<List<FriendData>> call, Response<List<FriendData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 请求成功，更新LiveData
                    List<FriendData> friends = response.body();
                    //Log.e("谁的问题", "Error fetching messages："+friends.get(0).getStatus());
                    friendDataList.clear();
                    friendDataList.addAll(friends);
                    friendNewAdapter.notifyDataSetChanged();
                    srl.setRefreshing(false); // 结束刷新动画
                } else {
                    // 处理HTTP错误，例如显示错误信息或重试
                    Toast.makeText(FriendNew.this, "网络错误！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FriendData>> call, Throwable t) {
                // 网络请求失败处理
                Log.e("HomeViewModel", "Error fetching messages", t);
                // 可以在这里处理错误情况，例如展示错误提示给用户
                NetworkUtils.handleApiError(FriendNew.this,call,t);
            }
        });

    }

    private void onFriendItemClick(FriendData data) {

        Integer userId = UserDataSingleton.getInstance().getUserId();

        Log.d("TAG", userId+"我同意加"+data.getShowName()+"为好友");

        apiService.friendsAgree(data.getAccount(), userId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body().equals("OK")) {
                    Log.d("TAG", "OK");
                    data.setStatus("agree");
                    friendNewAdapter.notifyDataSetChanged();
                } else {
                    // 处理HTTP错误，例如显示错误信息或重试
                    Toast.makeText(FriendNew.this, "添加失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // 网络请求失败处理
                Log.e("HomeViewModel", "Error fetching messages", t);
                // 可以在这里处理错误情况，例如展示错误提示给用户
                NetworkUtils.handleApiError(FriendNew.this,call,t);
            }
        });
    }



}
