package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = "AddPostActivity";
    EditText date, location, pic;
    Button post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        pic = findViewById(R.id.pic);
        post = findViewById(R.id.post);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posts posts = new Posts(GlobalData.user.getEmailId(),date.getText().toString(),location.getText().toString(),
                        pic.getText().toString(),1, "open", "iddd");

                Call<Posts> ins_post = GlobalData.postApi.insert_post(posts);
                ins_post.enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        Log.d(TAG, "onResponse: " + response);
                    }

                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                    }
                });

            }
        });

    }
}