package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MessageBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);

        Intent id = getIntent();
        String board_id = id.getExtras().getString("board_id");

        Toast.makeText(MessageBoardActivity.this, ""+board_id, Toast.LENGTH_SHORT).show();
    }
}