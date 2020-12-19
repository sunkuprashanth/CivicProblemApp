package com.example.civicproblemsapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalData {

    public static String LOGS_PREFS = "logged_file";

    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://trial8.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static UserApi userApi = retrofit.create(UserApi.class);

}
