package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView name, mobile, email_id;
    Button log_out;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences(GlobalData.LOGS_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();


        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email_id = findViewById(R.id.email_id);
        log_out = findViewById(R.id.sign_out);

        name.setText(GlobalData.user.getName());
        mobile.setText(GlobalData.user.getMobile());
        email_id.setText(GlobalData.user.getEmailId());

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.putBoolean("logged", false);
                editor.commit();
                Intent go_back = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(go_back);
            }
        });
    }
}