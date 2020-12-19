package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name, mobile, email_id, pass, c_pass;
    String name_str, mobile_str, email_id_str, pass_str, c_pass_str;
    Button sign_up;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email_id = findViewById(R.id.email_id);
        pass = findViewById(R.id.pass);
        c_pass = findViewById(R.id.c_pass);
        sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_str = name.getText().toString();
                mobile_str = mobile.getText().toString();
                email_id_str = email_id.getText().toString();
                pass_str = pass.getText().toString();
                c_pass_str = c_pass.getText().toString();

                if (c_pass_str.equals(pass_str)) {

                    Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();

                    sharedPreferences = getSharedPreferences(GlobalData.LOGS_PREFS, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("logged",true);
                    editor.commit();

                    Intent logged_in = new Intent(RegisterActivity.this, CivicPostsActivity.class);
                    startActivity(logged_in);
                }
            }
        });
    }
}