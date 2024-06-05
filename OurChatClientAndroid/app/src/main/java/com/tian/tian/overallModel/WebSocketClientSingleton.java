package com.tian.tian.overallModel;

import android.util.Log;

import com.tian.tian.httpService.WebSocketService.MessageUpdateListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class WebSocketClientSingleton {
    private static WebSocketClient instance;
    private URI serverUri;
    private static List<MessageUpdateListener> listeners = new ArrayList<>();

    public static void addListener(MessageUpdateListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(MessageUpdateListener listener) {
        listeners.remove(listener);
    }

    private WebSocketClientSingleton(URI serverUri) {
        this.serverUri = serverUri;
        instance = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("WebSocket", "连接成功");
            }

            @Override
            public void onMessage(String message) {
                Log.d("WebSocket", "接收: " + message);
                //更新消息列表
                //Gson gson = new Gson();
                //ChatMessageOne chatMessage = gson.fromJson(message, ChatMessageOne.class);
                for (MessageUpdateListener listener : listeners) {
                    listener.onMessageUpdate(message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d("WebSocket", "服务端关闭连接: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.e("WebSocket", "错误: " + ex.getMessage(), ex);
            }
        };
    }

    public static WebSocketClient getInstance(String url) {
        if (instance == null) {
            try {
                instance = new WebSocketClientSingleton(new URI(url)).instance;
                instance.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static void sendMessage(String message) {
        if (instance != null && instance.getConnection().isOpen()) {
            instance.send(message);
        } else {
            // 连接未建立或已关闭，根据需要处理这种情况，比如重连或提示用户
            Log.e("WebSocket", "WebSocket is not open. Cannot send message.");
        }
    }

    //关闭连接
    public static void closeConnection() {
        if (instance != null && instance.getConnection().isOpen()) {
            instance.close();
            instance=null;
        }
    }

}
