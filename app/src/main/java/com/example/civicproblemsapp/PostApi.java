package com.example.civicproblemsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi {

    @POST("/posts/insert")
    Call<Posts> insert_post(@Body Posts posts);

    @POST("/posts/update")
    Call<Posts> update_post(@Body Posts posts);

    @GET("/posts")
    Call<List<Posts>> get_posts();

    @GET("/posts/{emailId}")
    Call<List<Posts>> get_posts_user(@Path("emailId") String emailId);
}
