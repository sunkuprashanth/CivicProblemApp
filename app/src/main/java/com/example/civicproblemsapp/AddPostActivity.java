package com.example.civicproblemsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = "AddPostActivity";
    EditText date, location;
    Button post, choose_file;
    ImageView img;

    Uri file;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        post = findViewById(R.id.post);
        img = findViewById(R.id.img);
        choose_file = findViewById(R.id.choose);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd     HH:mm:ss");
        Date date_d = new Date();
        date.setText(dateFormat.format(date_d));
        date.setFocusable(false);


        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 908);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==908 && data!=null && resultCode == RESULT_OK) {
            file = data.getData();
            Log.d(TAG, "onActivityResult: " + file.toString());
            img.setVisibility(View.VISIBLE);
            Picasso.get().load(file).into(img);
        }
    }

    public void upload_mongo(Uri url) {
        Posts posts = new Posts(GlobalData.user.getEmailId(),date.getText().toString(),location.getText().toString(),
                url.toString(),1, "open", "iddd");

        posts.getLiked().add(GlobalData.user.getEmailId());
        Log.d(TAG, "upload_mongo: " + posts.getLiked().toString());

        Call<Posts> ins_post = GlobalData.postApi.insert_post(posts);
        ins_post.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                Log.d(TAG, "onResponse: " + response);
                Toast.makeText(AddPostActivity.this, "Post Uploaded Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddPostActivity.this, CivicPostsActivity.class));
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }

    public void upload() {

        final String file_name = "" + System.currentTimeMillis();
        final StorageReference riversRef = mStorageRef.child("images/h"+file_name + ".jpg");
        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Log.d(TAG, "onComplete: "+task.getResult());
                                Uri url = task.getResult();
                                upload_mongo(url);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d(TAG, "onFailure: ");
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(TAG, "onCanceled: ");
                    }
                });

    }
}