package com.example.civicproblemsapp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalData {

    public static UserDetails user = new UserDetails();

    public static List<Posts> posts = new ArrayList<Posts>();
    public static List<Posts> my_posts = new ArrayList<Posts>();
    public static List<Chats> chats = new ArrayList<Chats>();

    public static String LOGS_PREFS = "logged_file";

    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://trial8.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static UserApi userApi = retrofit.create(UserApi.class);
    public static PostApi postApi = retrofit.create(PostApi.class);
    public static ChatsApi chatsApi = retrofit.create(ChatsApi.class);

}
