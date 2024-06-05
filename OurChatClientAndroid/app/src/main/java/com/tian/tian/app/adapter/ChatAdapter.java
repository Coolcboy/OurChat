package com.tian.tian.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tian.tian.R;
import com.tian.tian.httpModel.ChatMessageOne;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private static final int VIEW_TYPE_LEFT = 0;
    private static final int VIEW_TYPE_RIGHT = 1;

    private Context context;
    private List<ChatMessageOne> chatMessages;
    private Integer currentUserId; // 当前用户的ID，可以通过构造函数传入

    public ChatAdapter(Context context, List<ChatMessageOne> chatMessages, Integer currentUserId) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageOne message = chatMessages.get(position);
        return message.getSenderId().equals(currentUserId) ? VIEW_TYPE_RIGHT : VIEW_TYPE_LEFT;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        switch (viewType) {
            case VIEW_TYPE_LEFT:
                itemView = inflater.inflate(R.layout.chat_item, parent, false);
                break;
            case VIEW_TYPE_RIGHT:
                itemView = inflater.inflate(R.layout.chat_right_item, parent, false);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
        return viewType == VIEW_TYPE_LEFT ? new LeftViewHolder(itemView) : new RightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessageOne message = chatMessages.get(position);
        holder.bind(message); // 在 ViewHolder 类中定义一个 bind 方法来设置数据
    }


    @Override
    public int getItemCount() {
        return chatMessages != null ? chatMessages.size() : 0;
    }

    // 定义两个 ViewHolder 类
    public abstract static class ChatViewHolder extends RecyclerView.ViewHolder {
        public ChatViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(ChatMessageOne message);
    }

    // 左侧消息 ViewHolder
    public static class LeftViewHolder extends ChatViewHolder {
        TextView tvContent_l;
        // ...其他需要的视图...

        public LeftViewHolder(View itemView) {
            super(itemView);
            tvContent_l = itemView.findViewById(R.id.tvContent_l);
            // 初始化其他视图...
        }

        @Override
        public void bind(ChatMessageOne message) {
            tvContent_l.setText(message.getContent());
            // 绑定其他数据...
        }
    }

    // 右侧消息 ViewHolder
    public static class RightViewHolder extends ChatViewHolder {
        TextView tvContent_r;

        // ...其他需要的视图...

        public RightViewHolder(View itemView) {
            super(itemView);
            tvContent_r = itemView.findViewById(R.id.tvContent_r);
            // 初始化其他视图...
        }

        @Override
        public void bind(ChatMessageOne message) {
            tvContent_r.setText(message.getContent());

            // 绑定其他数据...
        }
    }

}