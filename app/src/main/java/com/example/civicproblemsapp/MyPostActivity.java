package com.example.civicproblemsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostActivity extends AppCompatActivity {

    private static final String TAG = "MyPostActivity";
    LinearLayout post_ly;
    SharedPreferences sharedPreferences;
    String userId;
    TextView no_post;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        no_post = findViewById(R.id.no_post);

        dialog =  new ProgressDialog(MyPostActivity.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait while loading");
        dialog.show();

        sharedPreferences = getSharedPreferences(GlobalData.LOGS_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id","");
        post_ly = findViewById(R.id.post_ly);

        getPosts();

    }


    public void getPosts() {
        Call<List<Posts>> get = GlobalData.postApi.get_posts_user(userId);
        get.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                Log.d(TAG, "onResponse: "+response.body());
                if(response.body()!=null) {
                    GlobalData.my_posts = response.body();

                    if (GlobalData.my_posts.size()==0) {
                        no_post.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                    else
                        show_temps();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

            }
        });
    }
    public void show_temps() {

        int size = GlobalData.my_posts.size();
        for(int i=0; i<size; i++) {
            View main_ly =getLayoutInflater().inflate(R.layout.post, null, false);

            TextView user_name = main_ly.findViewById(R.id.user_name);
            TextView date_time = main_ly.findViewById(R.id.date_time);
            ImageView img = main_ly.findViewById(R.id.post_pic);
            final LinearLayout up_vote = main_ly.findViewById(R.id.up_vote);
            TextView status = main_ly.findViewById(R.id.status);
            Spinner spinner = main_ly.findViewById(R.id.spinner);
            Button msg_board = main_ly.findViewById(R.id.chat_board);
            LinearLayout vote_ly = up_vote.findViewById(R.id.vote_ly);
            TextView vote_count = vote_ly.findViewById(R.id.post_count);
            Button edit = main_ly.findViewById(R.id.edit);

            TextView location = main_ly.findViewById(R.id.loc);
            location.setText(GlobalData.posts.get(i).getLocation());

            edit.setVisibility(View.VISIBLE);

            ImageView up = up_vote.findViewById(R.id.img_up);
            up.setImageDrawable(getResources().getDrawable(R.drawable.up_voted));

            user_name.setText(GlobalData.my_posts.get(i).getUser());
            date_time.setText(GlobalData.my_posts.get(i).getDate());
            vote_count.setText(""+GlobalData.my_posts.get(i).getUpVoteCount());
            status.setText(GlobalData.my_posts.get(i).getStatus());

            final Posts post = GlobalData.my_posts.get(i);
            Picasso.get().load(Uri.parse(post.getImg_url())).into(img);

            spinner.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MyPostActivity.this, "Editing", Toast.LENGTH_SHORT).show();

                    Intent edit = new Intent(MyPostActivity.this, EditPostActivity.class);
                    edit.putExtra("_id",post.get_id().toString());
                    startActivity(edit);
                }
            });

            up_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(MyPostActivity.this, "Already Voted", Toast.LENGTH_SHORT).show();

                }
            });

            msg_board.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent msg = new Intent(MyPostActivity.this, MessageBoardActivity.class);
                    msg.putExtra("board_id",post.get_id().toString());
                    startActivity(msg);
                }
            });

            post_ly.addView(main_ly);
        }
        dialog.dismiss();

    }
}