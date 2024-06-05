package com.tian.tian.app.adapter.friend_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tian.tian.R;
import com.tian.tian.httpModel.FriendData;

import java.util.List;

public class FriendNewAdapter extends RecyclerView.Adapter<FriendNewAdapter.ViewHolder>  {

    private Context context;
    private List<FriendData> friendDataList;
    private Integer currentUserId;
    private FriendNewAdapter.OnItemClickListener mListener;
    public FriendNewAdapter(Context context, List<FriendData> friendDataList, Integer currentUserId, FriendNewAdapter.OnItemClickListener listener){
        this.context = context;
        this.friendDataList = friendDataList;
        this.currentUserId = currentUserId;
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(FriendData data);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public FriendNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_new_page_item, parent, false);
        return new FriendNewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendNewAdapter.ViewHolder holder, int position) {
        FriendData data = friendDataList.get(position);
        holder.nameTextView.setText(data.getShowName());

        //显示点击按钮后的文本
        holder.addButton.setVisibility(data.getStatus().equals("agree") ? View.GONE : View.VISIBLE);
        holder.tvAgreed.setVisibility(data.getStatus().equals("agree") ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return friendDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button addButton;

        TextView tvAgreed;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.fiName);
            tvAgreed = itemView.findViewById(R.id.tvAgreed);
            addButton = itemView.findViewById(R.id.btnAdd); // 初始化按钮引用
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // 获取点击按钮所在的位置
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onItemClick(friendDataList.get(position)); // 传递点击的 item 数据
                    }
                }
            });


        }
    }

}
