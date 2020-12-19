package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CivicPostsActivity extends AppCompatActivity {

    private static final String TAG = "CivicPostsActivity";
    LinearLayout post_ly;
    CircleImageView profile_pic;
    Button add_post, your_post;
    Button log_out;
    int i=0;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civic_posts);

        dialog =  new ProgressDialog(CivicPostsActivity.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait while loading");
        dialog.show();

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


        getPosts();
    }

    public void getPosts() {
        Call<List<Posts>> get = GlobalData.postApi.get_posts();
        get.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                Log.d(TAG, "onResponse: " + response.body());
                GlobalData.posts = response.body();
                show_temps();
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

            }
        });
    }
    public void show_temps() {

        int size = GlobalData.posts.size();
        for(i=0; i<size; i++) {
            View main_ly =getLayoutInflater().inflate(R.layout.post, null, false);

            TextView user_name = main_ly.findViewById(R.id.user_name);
            TextView date_time = main_ly.findViewById(R.id.date_time);
            ImageView img = main_ly.findViewById(R.id.post_pic);
            final LinearLayout up_vote = main_ly.findViewById(R.id.up_vote);
            TextView status = main_ly.findViewById(R.id.status);
            Button msg_board = main_ly.findViewById(R.id.chat_board);
            LinearLayout vote_ly = up_vote.findViewById(R.id.vote_ly);
            TextView vote_count = vote_ly.findViewById(R.id.post_count);

            user_name.setText(GlobalData.posts.get(i).getUser());
            date_time.setText(GlobalData.posts.get(i).getDate());
            vote_count.setText(""+GlobalData.posts.get(i).getUpVoteCount());
            status.setText(GlobalData.posts.get(i).getStatus());
            final int item = i;
            final Posts post = GlobalData.posts.get(item);
            Log.d(TAG, "show_temps: " + post.get_id());

            up_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView up = up_vote.findViewById(R.id.img_up);
                    up.setImageDrawable(getResources().getDrawable(R.drawable.up_voted));
                    post.setUpVoteCount(post.getUpVoteCount()+1);
                    Call<Posts> update = GlobalData.postApi.update_post(post);
                    update.enqueue(new Callback<Posts>() {
                        @Override
                        public void onResponse(Call<Posts> call, Response<Posts> response) {
                            Log.d(TAG, "onResponse: " + response.body());
                            Toast.makeText(CivicPostsActivity.this, "Voted for this post", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Posts> call, Throwable t) {
                            Log.d(TAG, "onFailure: ");
                        }
                    });

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
        dialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}