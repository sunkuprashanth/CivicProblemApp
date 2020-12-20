package com.example.civicproblemsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends AppCompatActivity {


    private static final String TAG = "EditPostActivity";
    EditText date, location;
    Button post, choose_file, delete_post;
    ImageView img;
    Posts editable_post;
    Uri file;
    ProgressDialog dialog;
    String id;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        dialog =  new ProgressDialog(EditPostActivity.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait while loading");
        dialog.show();

        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        post = findViewById(R.id.post);
        img = findViewById(R.id.img);
        choose_file = findViewById(R.id.choose);
        delete_post = findViewById(R.id.delete);

        Intent get = getIntent();
        id = get.getExtras().getString("_id");
        id = id.substring(6,id.indexOf('}'));
        Log.d(TAG, "onCreate: " + id);


        Call<Posts> get_post_id = GlobalData.postApi.get_post_id(id);
        get_post_id.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(response.body()!=null) {

                    img.setVisibility(View.VISIBLE);
                    editable_post = response.body();
                    date.setText(editable_post.getDate());
                    location.setText(editable_post.getLocation());
                    Picasso.get().load(Uri.parse(editable_post.getImg_url())).into(img);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });

        delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<HashMap<String,String>> res = GlobalData.postApi.delete_post(editable_post);
                res.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        if (response.code()==200) {
                            Log.d(TAG, "onResponse: Deleted");
                            Toast.makeText(EditPostActivity.this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

                    }
                });
            }
        });

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

        if (file==null) {
            url = Uri.parse(editable_post.getImg_url());
        }
        Posts posts = new Posts(GlobalData.user.getEmailId(),date.getText().toString(),location.getText().toString(),
                url.toString(),editable_post.getUpVoteCount(), editable_post.getStatus(), editable_post.getMessageBoard());
        posts.set_id(editable_post.get_id());
        posts.setId(id);
        Log.d(TAG, "upload_mongo: " + editable_post.get_id());
        Call<Posts> ins_post = GlobalData.postApi.update_post(posts);
        ins_post.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                Log.d(TAG, "onResponse: " + response.body());
                if (response.body()!=null) {
                    Toast.makeText(EditPostActivity.this, "Post Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditPostActivity.this, CivicPostsActivity.class));
                }
                else
                    Toast.makeText(EditPostActivity.this, "" + response.code() + " Error Occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }
    public void upload(){

        final String file_name = "" + System.currentTimeMillis();
        if (file==null) {
            upload_mongo(null);
        }
        else {
            final StorageReference riversRef = mStorageRef.child("images/h" + file_name + ".jpg");
            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Log.d(TAG, "onComplete: " + task.getResult());
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
}