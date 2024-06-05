package com.tian.tian.httpService;
import com.tian.tian.httpModel.ChatMessageOne;
import com.tian.tian.httpModel.FriendData;
import com.tian.tian.httpModel.LoginRequest;
import com.tian.tian.httpModel.LoginResponse;
import com.tian.tian.httpModel.MessageResponse;
import com.tian.tian.httpModel.RegisterRequest;
import com.tian.tian.httpModel.UserData;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("chat/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("chat/register")
    Call<LoginResponse> register(@Body RegisterRequest registerRequest);

    @GET("chat/messages")
    Call<List<MessageResponse>> fetchNewMessages( @Query("account") Integer account);

    @GET("chat/friends")
    Call<List<FriendData>> getFriends(@Query("account") Integer account);
    @GET("chat/friends_new")
    Call<List<FriendData>> getFriendsNew(@Query("account") Integer account);
    @GET("chat/friends_agree")
    Call<String> friendsAgree(@Query("sender_id") Integer sender_id,@Query("receiver_id") Integer receiver_id);

    @GET("chat/getUserData")
    Call<UserData> getUserData(@Query("account") Integer account);

    @POST("chat/getMessageOne")
    Call<List<ChatMessageOne>> getMessageOne(@Body ChatMessageOne ChatMessageOne);


    //菜单
    @GET("menu/addFriend")
    Call<String> addFriend(@Query("sender_id") Integer sender_id,@Query("receiver_id") Integer receiver_id);

}
