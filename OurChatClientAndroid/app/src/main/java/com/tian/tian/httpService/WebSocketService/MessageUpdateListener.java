package com.tian.tian.httpService.WebSocketService;

import com.tian.tian.httpModel.ChatMessageOne;

public interface MessageUpdateListener {
    void onMessageUpdate(String message);
}
