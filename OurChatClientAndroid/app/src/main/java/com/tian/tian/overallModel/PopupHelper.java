package com.tian.tian.overallModel;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.tian.tian.LoginActivity;
import com.tian.tian.R;
import com.tian.tian.app.add_menu.MenuFriendAdd;
import com.tian.tian.app.home.HomeActivity;

public class PopupHelper {

    //顶部栏加号小菜单
    public static void setupSearchAndAddButtons(View view, FragmentActivity activity) {
        View ztSearch = view.findViewById(R.id.ztSearch);
        View ztAdd = view.findViewById(R.id.ztAdd);

        ztSearch.setOnClickListener(v -> NetworkUtils.functionDeveloping(activity));

        ztAdd.setOnClickListener(v -> showPopupMenu(v, activity));
    }

    public static void showPopupMenu(View anchorView, FragmentActivity activity) {
        // inflate the menu from xml
        View popupView = activity.getLayoutInflater().inflate(R.layout.home_add_menu, null);
        // create and setup the PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // show the PopupWindow
        //显示在右上角
        // 获取屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        // 计算PopupWindow在屏幕右侧出现的位置
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int popupX = screenWidth - (location[0] + anchorView.getWidth()); // 使PopupWindow的右边缘对齐anchorView的右边缘
        // 确保PopupWindow显示在anchorView的正上方
        popupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.END, popupX, location[1]+165);

        View startGroupChatButton = popupView.findViewById(R.id.onStartGroupChatClick);
        startGroupChatButton.setOnClickListener(v -> {
            // 处理发起群聊逻辑
            NetworkUtils.functionDeveloping(activity);
            // 关闭popupWindow
            popupWindow.dismiss();
        });
        View onStartFriendClick = popupView.findViewById(R.id.onStartFriendClick);
        onStartFriendClick.setOnClickListener(v -> {
            // 处理加朋友
            Intent intent = new Intent(activity, MenuFriendAdd.class);
            activity.startActivity(intent);
            popupWindow.dismiss();
        });
        View onStartScanClick = popupView.findViewById(R.id.onStartScanClick);
        onStartScanClick.setOnClickListener(v -> {
            // 处理扫一扫
            NetworkUtils.functionDeveloping(activity);
            popupWindow.dismiss();
        });
        View onStartMoneyClick = popupView.findViewById(R.id.onStartMoneyClick);
        onStartMoneyClick.setOnClickListener(v -> {
            // 处理收付款
            NetworkUtils.functionDeveloping(activity);
            popupWindow.dismiss();
        });

    }


}
