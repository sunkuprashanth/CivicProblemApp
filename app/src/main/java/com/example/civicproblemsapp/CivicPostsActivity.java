package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class CivicPostsActivity extends AppCompatActivity {

    LinearLayout post_ly;
    CircleImageView profile_pic;
    Button add_post, your_post;
    Button log_out;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civic_posts);

        post_ly = findViewById(R.id.post_ly);
        profile_pic = findViewById(R.id.profilePic);
        add_post = findViewById(R.id.add_post);
        your_post = findViewById(R.id.your_post);
        log_out = findViewById(R.id.log_out);

        sharedPreferences = getSharedPreferences(GlobalData.LOGS_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_post = new Intent(CivicPostsActivity.this, ProfileActivity.class);
                startActivity(add_post);
            }
        });
        your_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_post = new Intent(CivicPostsActivity.this, MyPostActivity.class);
                startActivity(add_post);
            }
        });
        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_post = new Intent(CivicPostsActivity.this, AddPostActivity.class);
                startActivity(add_post);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.putBoolean("logged", false);
                editor.commit();
                Intent go_back = new Intent(CivicPostsActivity.this, MainActivity.class);
                startActivity(go_back);
            }
        });


        show_temps();
    }

    public void show_temps() {

        int size = 10;
        for(int i=0; i<size; i++) {
            View main_ly =getLayoutInflater().inflate(R.layout.post, null, false);

            TextView user_name = main_ly.findViewById(R.id.user_name);
            TextView date_time = main_ly.findViewById(R.id.date_time);
            ImageView img = main_ly.findViewById(R.id.post_pic);
            final LinearLayout up_vote = main_ly.findViewById(R.id.up_vote);
            TextView status = main_ly.findViewById(R.id.status);
            Button msg_board = main_ly.findViewById(R.id.chat_board);

            user_name.setText(" Id: "+i);

            up_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView up = up_vote.findViewById(R.id.img_up);
                    up.setImageDrawable(getResources().getDrawable(R.drawable.up_voted));
                }
            });

            msg_board.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CivicPostsActivity.this, "Messaging not implemented Yet", Toast.LENGTH_SHORT).show();
                }
            });

            post_ly.addView(main_ly);
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}