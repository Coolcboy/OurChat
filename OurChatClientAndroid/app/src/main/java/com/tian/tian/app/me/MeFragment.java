package com.tian.tian.app.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tian.tian.R;

public class MeFragment extends Fragment {

    private MeViewModel mViewModel;
    private TextView tvName, tvSignature,ztSet,ztIntroduction;
    private SwipeRefreshLayout srl;

    private Integer account;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        account = getArguments().getInt("account", 0);

        // 初始化视图
        tvName = view.findViewById(R.id.tvName);
        tvSignature = view.findViewById(R.id.tvSignature);
        ztSet = view.findViewById(R.id.ztSet);
        ztIntroduction = view.findViewById(R.id.ztIntroduction);
        srl = view.findViewById(R.id.srl);

        // 设置下拉刷新监听器
        srl.setOnRefreshListener(this::refreshData);

        // 初始化 ViewModel
        mViewModel = new ViewModelProvider(this).get(MeViewModel.class);

        //手动调用
        refreshData();
        // 观察数据变化
        observeUserData();

        //设置
        ztSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MePageSet.class);
                startActivity(intent);
            }
        });

        ztIntroduction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), AboutAuthor.class);
                startActivity(intent);
            }
        });

    }

    private void refreshData() {
        // 刷新数据，这里可以通过ViewModel发起请求
        mViewModel.fetchUserData(account);
        srl.setRefreshing(true); // 开始刷新动画
    }

    private void observeUserData() {
        mViewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            if (userData != null) {
                // 在主线程更新UI
                tvName.setText(userData.getName());
                tvSignature.setText(getString(R.string.account_label, String.valueOf(userData.getAccount())));

                // 停止刷新动画
                srl.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment, container, false);
    }

    public static MeFragment newInstance(Integer account) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putInt("account", account);
        fragment.setArguments(args);
        return fragment;
    }


}
