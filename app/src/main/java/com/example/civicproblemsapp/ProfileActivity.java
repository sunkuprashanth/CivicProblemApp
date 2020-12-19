package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView name, mobile, email_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email_id = findViewById(R.id.email_id);

        name.setText(GlobalData.user.getName());
        mobile.setText(GlobalData.user.getMobile());
        email_id.setText(GlobalData.user.getEmailId());
    }
}