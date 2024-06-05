package com.tian.tian.overallModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.HttpException;

public class NetworkUtils {

    // 将Error方法改为静态方法，并接收额外的Context参数
    public static void handleApiError(Context context, Call<?> call, Throwable t) {
        if (context == null) {
            // 如果没有有效的Context，记录错误但不尝试显示Toast
            Log.e("NetworkUtils", "Error handling with null Context", t);
            return;
        }
        if (t instanceof IOException) {
            // 网络连接问题
            Toast.makeText(context, "网络连接失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
            Log.e("NetworkUtils", "网络错误：", t);
        } else if (t instanceof SocketTimeoutException) {
            // 请求超时
            Toast.makeText(context, "请求超时，请稍后重试", Toast.LENGTH_SHORT).show();
        } else if (t instanceof HttpException) {
            // HTTP错误
            HttpException httpException = (HttpException) t;
            int code = httpException.code();
            if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // 未授权
                Toast.makeText(context, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                // 其他HTTP错误码
                Toast.makeText(context, "服务器错误，错误码: " + code, Toast.LENGTH_SHORT).show();
            }
        } else {
            // 其他未知错误
            Toast.makeText(context, "发生了一个未知错误，请稍后重试", Toast.LENGTH_SHORT).show();
            Log.e("NetworkUtils", "请求失败", t);
        }
    }


    public static void functionDeveloping(Context context){
        Toast.makeText(context,"功能开发ing",Toast.LENGTH_SHORT).show();
    }
}
