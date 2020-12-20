package com.example.civicproblemsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatsApi {

    @POST("/chats/insert")
    Call<Chats> chat_insert(@Body Chats chats);

    @POST("/chats")
    Call<List<Chats>> get_chats(@Body String board_id);

    @POST("/chats/{id}")
    Call<List<Chats>> get_chats2(@Path("id") String board_id);

}
