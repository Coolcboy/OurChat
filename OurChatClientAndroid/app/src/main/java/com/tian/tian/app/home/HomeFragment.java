package com.tian.tian.app.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.tian.tian.R;
import com.tian.tian.app.adapter.MessageAdapter;
import com.tian.tian.app.chat.ChatActivity;
import com.tian.tian.httpModel.MessageResponse;
import com.tian.tian.overallModel.PopupHelper;

public class HomeFragment extends Fragment implements MessageAdapter.OnItemClickListener {

    private HomeViewModel mViewModel;
    private MessageAdapter mAdapter;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    private Integer account;

    public static HomeFragment newInstance(Integer account) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("account", account);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        account = getArguments().getInt("account", 0);

        // 初始化 RecyclerView
        rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        // 初始化SwipeRefreshLayout
        srl = rootView.findViewById(R.id.srl);
        srl.setColorSchemeResources(R.color.colorAccent);
        srl.setOnRefreshListener(this::refreshMessages);

        // 初始化 ViewModel 和 Adapter
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mAdapter = new MessageAdapter(getActivity(), this); // 传递当前fragment作为点击监听器
        rv.setAdapter(mAdapter);

        //自动调用一次
        refreshMessages();
        // 观察ViewModel中的LiveData
        observeViewModel();
        // 添加对错误信息的观察
        observeError();

        return rootView;
    }

    private void observeViewModel() {
        mViewModel.getMessages().observe(getViewLifecycleOwner(), mAdapter::setData);
        //srl.setRefreshing(false);
    }

    private void refreshMessages() {
        // fetchNewMessages() 方法用于获取最新消息
        mViewModel.fetchNewMessages(account);
        // 设置正在刷新状态，显示刷新指示器
        srl.setRefreshing(true);
        srl.setRefreshing(false);
    }

    private void observeError() {
        mViewModel.getError().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                mViewModel.setError(null); // 清除错误信息
            }
        });
    }


    // 实现MessageAdapter.OnItemClickListener接口的方法
    @Override
    public void onItemClick(MessageResponse message) {
        clickItem(message); // 跳转到聊天界面的逻辑
    }

    private void clickItem(MessageResponse message) {
        // 这里实现跳转到聊天界面的逻辑
        // 可能包括传递message的唯一标识符到ChatActivity等
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        Integer oppositeAccount = message.getOppositeAccount(); // 对方账号
        String oppositeName = message.getName();
        //userDataSingleton.setUserId(account);
        intent.putExtra("account", account.toString()); // 我的号
        intent.putExtra("oppositeAccount", oppositeAccount.toString());
        intent.putExtra("oppositeName", oppositeName);
        startActivity(intent);
    }

    //UI初始化完成后
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView toolbarName = getView().findViewById(R.id.tvTitle);
        toolbarName.setText("微信2.0");
        PopupHelper.setupSearchAndAddButtons(view, getActivity());//顶部按钮调用
    }


}