package com.example.civicproblemsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    //@GET("/users/{name}/{mobile}/{emailId}/{password}")
    //Call<UserDetails> insertUser(@Path("name") String name, @Path("mobile") String mobile, @Path("emailId") String emailId, @Path("password") String password);

    @POST("/users/insert")
    Call<UserDetails> insertUser(@Body UserDetails ud);

    @GET("/users/login/{emailId}/{password}")
    Call<UserDetails> getUserAuth(@Path("emailId") String emailId, @Path("password") String password);

    @GET("/users/{userId}")
    Call<UserDetails> getUser(@Path("userId") String userToken);

}
