package com.tian.tian.app.friend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tian.tian.R;
import com.tian.tian.app.adapter.FriendAdapter;
import com.tian.tian.app.chat.ChatActivity;
import com.tian.tian.httpModel.FriendData;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.retrofit.RetrofitClient;
import com.tian.tian.overallModel.NetworkUtils;
import com.tian.tian.overallModel.PopupHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendAdapter mAdapter;
    private List<FriendData> mFriendList = new ArrayList<>();
    private ApiService apiService;
    private Integer account;
    private View newFriendClick,GroupChatClick;

    public static FriendFragment newInstance(Integer account) {
        FriendFragment friendFragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putInt("account", account);
        friendFragment.setArguments(args);
        return friendFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_fragment, container, false);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class); // 初始化Retrofit服务
        account = getArguments().getInt("account", 0);

        // 初始化RecyclerView
        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 初始化SwipeRefreshLayout并设置监听器
        mSwipeRefreshLayout = view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // 初始化Adapter并设置到RecyclerView
        mAdapter = new FriendAdapter(getContext(), mFriendList, this::onFriendItemClick);
        mRecyclerView.setAdapter(mAdapter);

        // 加载数据
        loadData(account);

        return view;
    }

    private void loadData(Integer account) {
        apiService.getFriends(account).enqueue(new Callback<List<FriendData>>() {
            @Override
            public void onResponse(Call<List<FriendData>> call, Response<List<FriendData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 请求成功，更新LiveData
                    List<FriendData> friends = response.body();
                    mFriendList.clear();
                    mFriendList.addAll(friends);
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false); // 结束刷新动画
                } else {
                    // 处理HTTP错误，例如显示错误信息或重试
                    Log.e("HomeViewModel", "Error fetching messages");
                    Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FriendData>> call, Throwable t) {
                // 网络请求失败处理
                Log.e("HomeViewModel", "Error fetching messages", t);
                // 可以在这里处理错误情况，例如展示错误提示给用户
                NetworkUtils.handleApiError(getActivity(),call,t);
            }
        });

    }

    private void onFriendItemClick(FriendData data) {
        // 跳转到聊天页面的逻辑
        //Log.d("TAG", "This is a debug message");
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        Integer oppositeAccount = data.getAccount();
        //userDataSingleton.setUserId(account);
        intent.putExtra("account", account.toString());
        intent.putExtra("oppositeAccount", oppositeAccount.toString());
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        // 用户下拉刷新时调用此方法，重新加载数据
        loadData(account);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView toolbarName = getView().findViewById(R.id.tvTitle);
        toolbarName.setText("通讯录");

        PopupHelper.setupSearchAndAddButtons(view, getActivity());//顶部按钮调用

        //新的朋友and群聊
        newFriendClick = getView().findViewById(R.id.newFriendClick);
        newFriendClick.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FriendNew.class);
            startActivity(intent);
        });

        GroupChatClick = getView().findViewById(R.id.GroupChatClick);
        GroupChatClick.setOnClickListener(v -> {
            NetworkUtils.functionDeveloping(requireActivity());
        });

    }

}
