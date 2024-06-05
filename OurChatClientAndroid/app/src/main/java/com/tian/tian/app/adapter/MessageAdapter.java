package com.tian.tian.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tian.tian.R;
import com.tian.tian.httpModel.MessageResponse;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageResponse> messageList;
    private Context context;

    private OnItemClickListener listener;

    public MessageAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<MessageResponse> data) {
        this.messageList = data;
        Log.d("NBNBNBNBNBNBNB","元神11111111");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_message_item, parent, false);
        Log.d("NBNBNBNBNBNBNB","元神22222222");
        return new MessageViewHolder(itemView, this); // 传递Adapter实例给ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bindData(messageList.get(position), listener); // 将数据和监听器传给ViewHolder处理
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(MessageResponse message);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMessage, tvTime;
        //ImageView ivAvatar;

        MessageViewHolder(View itemView, final MessageAdapter adapter) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            //ivAvatar = itemView.findViewById(R.id.ivAvatar);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && adapter.listener != null) {
                    adapter.listener.onItemClick(adapter.messageList.get(position));
                }
            });
        }

        void bindData(MessageResponse message, OnItemClickListener clickListener) {
            // 绑定数据到视图的逻辑
            tvName.setText(message.getName());
            tvMessage.setText(message.getLast_message());
            tvTime.setText(message.getTime());

        }
    }
}