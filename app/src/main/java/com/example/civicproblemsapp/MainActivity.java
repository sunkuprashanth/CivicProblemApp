package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText email_id, pass;
    Button login;
    TextView register;
    String email_id_str, pass_str;
    boolean logged = true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_id = findViewById(R.id.email_id);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        register = findViewById(R.id.goto_register_link);

        sharedPreferences = getSharedPreferences(GlobalData.LOGS_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        logged = sharedPreferences.getBoolean("logged", false);

        if(logged) {
            Intent logged_in = new Intent(MainActivity.this, CivicPostsActivity.class);
            startActivity(logged_in);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_id_str = email_id.getText().toString();
                pass_str = pass.getText().toString();

                boolean valid_cred = false;

                Call<UserDetails> login_user = GlobalData.userApi.getUserAuth(email_id_str,pass_str);
                login_user.enqueue(new Callback<UserDetails>() {
                    @Override
                    public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                        Log.d(TAG, "onResponse: " + response.code() + "\nbody: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<UserDetails> call, Throwable t) {

                    }
                });


                if (valid_cred) {

                    editor.putBoolean("logged",true);
                    editor.commit();
                    Intent logged_in = new Intent(MainActivity.this, CivicPostsActivity.class);
                    startActivity(logged_in);
                }
                else
                    Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logged_in = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(logged_in);
            }
        });
    }
}

/*Retrofit retrofit = new Retrofit.Builder().baseUrl("https://trial8.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        TestingRestApi test = retrofit.create(TestingRestApi.class);

        Call<List<Users>> comms = test.getComments();

        comms.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                List<Users> us = response.body();
                Log.d(TAG, "onResponse: " + us.size());
                Log.d(TAG, "onResponse: " + us.get(us.size()-1).getLogin_id());
                for (Users u : us)
                    tv.setText(""+response.body().get(0).getPassword());
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

 */