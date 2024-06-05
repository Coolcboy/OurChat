package com.tian.tian.app.home;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tian.tian.httpModel.MessageResponse;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeViewModel extends ViewModel {
    private ApiService apiService;
    private MutableLiveData<List<MessageResponse>> messages = new MutableLiveData<>();
    //private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> error = new MutableLiveData<>();
    public void setError(String errorMsg) {
        error.setValue(errorMsg);
    }

    public LiveData<String> getError() {
        return error;
    }

    public HomeViewModel() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class); // 初始化Retrofit服务
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages.setValue(messages);
    }

    public LiveData<List<MessageResponse>> getMessages() {
        return messages;
    }

    public void fetchNewMessages(Integer account) {
        //isLoading.postValue(true);
        // 异步操作，从服务器或本地数据库获取新消息，并最终调用
        // messageListLiveData.postValue(更新后的消息列表);
        // 使用Retrofit发起异步请求
        apiService.fetchNewMessages(account).enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 请求成功，更新LiveData
                    setMessages(response.body());
                } else {
                    // 没有消息
                    setError("没有收到她的消息！");
                }
            }

            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                // 网络请求失败处理
                Log.e("HomeViewModel", "Error fetching messages", t);
                setError("获取消息失败，请检查网络连接！");
            }
        });
        //isLoading.postValue(false); // 数据加载结束
    }

//    //public LiveData<Boolean> getIsLoading() {
//        return isLoading;
//    }

}
