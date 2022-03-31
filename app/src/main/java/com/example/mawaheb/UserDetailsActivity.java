package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UserDetailsActivity extends AppCompatActivity {
    private ImageView imageUser;
    private EditText name;
    private EditText bio;
    private EditText email;
    private EditText phone;
    private EditText talents;
    private Button edit;
    private Button delete;

    private DatabaseReference mDatabase;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageUser = findViewById(R.id.imageUser);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        talents = findViewById(R.id.talents);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        name.setText(user.getName());
        bio.setText(user.getBio()+"");
        email.setText(user.getEmail()+"");
        phone.setText(user.getPhone()+"");
        talents.setText(user.getTalents()+"");

        if(user.getUserType().equals("Coordinator")){
            bio.setVisibility(View.GONE);
            talents.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            imageUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,142);
                }
            });
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri_select == null){
                    Toast.makeText(UserDetailsActivity.this, "Select image", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name.getText())){
                    name.setError("Enter Name");
                }else if(TextUtils.isEmpty(email.getText())){
                    email.setError("Enter Email");
                }else if(TextUtils.isEmpty(phone.getText())){
                    phone.setError("Enter Phone");
                }else{




                    imageUser.setDrawingCacheEnabled(true);
                    imageUser.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imageUser.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("UsersImages").child(user.getUID()+".jpeg");
                    UploadTask uploadTask = storageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            user.setName(name.getText().toString());
                            user.setPhone(phone.getText().toString());
                            user.setEmail(email.getText().toString());


                            mDatabase.child("users").child(user.getUID()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(UserDetailsActivity.this, "Coordinator Edited Successfully", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(UserDetailsActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });



                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserDetailsActivity.this).setMessage("Do you want to delete this coordinator?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child("users").child(user.getUID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("UsersImages").child(user.getUID()+".jpeg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(UserDetailsActivity.this)
                        .load(uri)
                        .into(imageUser);
                uri_select = uri;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    Uri uri_select;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 142 && resultCode == RESULT_OK){
            uri_select = data.getData();
            imageUser.setImageURI(uri_select);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}