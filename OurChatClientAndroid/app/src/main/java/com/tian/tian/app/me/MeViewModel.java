package com.tian.tian.app.me;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tian.tian.httpModel.UserData;
import com.tian.tian.httpService.ApiService;
import com.tian.tian.httpService.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeViewModel extends ViewModel {
    private MutableLiveData<UserData> userData = new MutableLiveData<>();
    private ApiService apiService; // 网络请求服务

    public MeViewModel() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchUserData(Integer account) {
        // 发起网络请求获取用户数据
        Log.d("MeViewModel", "发送请求1111111111");
        apiService.getUserData(account).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful() && response.body() != null) {

                    userData.postValue(response.body());
                } else {
                    // 错误处理
                    Log.e("MeViewModel", "Error fetching user data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                // 网络请求失败处理
                Log.e("MeViewModel", "Error fetching user data", t);
            }
        });
    }

    public LiveData<UserData> getUserData() {
        return userData;
    }
}
