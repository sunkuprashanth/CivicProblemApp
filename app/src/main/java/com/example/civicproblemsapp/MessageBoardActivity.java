package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageBoardActivity extends AppCompatActivity {

    private static final String TAG = "MessageBoardActivity";
    LinearLayout chat_space;
    EditText msg;
    Button send;
    String board_id;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);

        chat_space = findViewById(R.id.chat_space);
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send);
        scroll = findViewById(R.id.scroll);

        Intent id = getIntent();
        board_id = id.getExtras().getString("board_id");

        //Toast.makeText(MessageBoardActivity.this, ""+board_id, Toast.LENGTH_SHORT).show();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_new_chats();
            }
        });

        add_prev_chats();
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void add_new_chats() {

        final String message = msg.getText().toString();
        Chats new_chat = new Chats(board_id, message, GlobalData.user.getEmailId(),System.currentTimeMillis());
        msg.setText("");

        if (message.equals(""))
            return;

        Call<Chats> ins_chat = GlobalData.chatsApi.chat_insert(new_chat);
        ins_chat.enqueue(new Callback<Chats>() {
            @Override
            public void onResponse(Call<Chats> call, Response<Chats> response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.body()==null) {
                    Toast.makeText(MessageBoardActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    View chat = getLayoutInflater().inflate(R.layout.chat_out,null,false);
                    TextView text_msg = chat.findViewById(R.id.text_msg);
                    text_msg.setText(message);
                    chat_space.addView(chat);
                    scroll_down(new View(MessageBoardActivity.this));

                }
            }

            @Override
            public void onFailure(Call<Chats> call, Throwable t) {

            }
        });

    }

    public void add_prev_chats() {

        Call<List<Chats>> get_chats = GlobalData.chatsApi.get_chats2(board_id);
        get_chats.enqueue(new Callback<List<Chats>>() {
            @Override
            public void onResponse(Call<List<Chats>> call, Response<List<Chats>> response) {
                Log.d(TAG, "onResponse: " + board_id + "  " +response);
                if(response.body()!=null) {
                    GlobalData.chats = response.body();
                    Log.d(TAG, "onResponse: " + GlobalData.chats.size());

                    for (int i = 0; i < GlobalData.chats.size(); i++) {
                        View chat;
                        if(GlobalData.chats.get(i).getSender().equals(GlobalData.user.getEmailId()))
                            chat = getLayoutInflater().inflate(R.layout.chat_out,null,false);
                        else
                            chat = getLayoutInflater().inflate(R.layout.chat_in,null,false);
                        TextView text_msg = chat.findViewById(R.id.text_msg);
                        text_msg.setText(GlobalData.chats.get(i).getMessage());
                        chat_space.addView(chat);

                        scroll_down(new View(MessageBoardActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Chats>> call, Throwable t) {

            }
        });

    }
    public void scroll_down(View v) {
        scroll.fullScroll(View.FOCUS_DOWN);
    }
}