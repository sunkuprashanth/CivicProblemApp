package com.example.civicproblemsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/users")
    Call<List<UserDetails>> insertUser(@Body UserDetails new_user);

    @GET("/users/login")
    Call<UserDetails> getUserAuth(@Body String emailId, @Body String password);

    @GET("/users/{userId}")
    Call<UserDetails> getUser(@Query("userId") String userToken);

}
