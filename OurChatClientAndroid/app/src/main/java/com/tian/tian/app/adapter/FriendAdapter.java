package com.tian.tian.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tian.tian.R;
import com.tian.tian.httpModel.FriendData;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context mContext;
    private List<FriendData> mList;
    private OnItemClickListener mListener; // 添加点击监听接口

    public interface OnItemClickListener {
        void onItemClick(FriendData data);
    }

    public FriendAdapter(Context context, List<FriendData> list, OnItemClickListener listener) {
        mContext = context;
        mList = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 绑定xml页面数据
        TextView nameTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.fiName);
            // 初始化其他视图...
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // xml页面数据设置
        FriendData data = mList.get(position);
        holder.nameTextView.setText(data.getShowName());
        // 设置ImageView的资源或使用Glide/Picasso加载图片

        // 设置点击监听
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(data));
    }

}
