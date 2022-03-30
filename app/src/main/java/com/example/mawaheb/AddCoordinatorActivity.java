package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddCoordinatorActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ImageView mSelectImage;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirpassword;
    private Button add;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcoordinator);
        getSupportActionBar().setTitle("Add Coordinator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSelectImage = findViewById(R.id.select_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        password = findViewById(R.id.password);
        add = findViewById(R.id.add);
        confirpassword = findViewById(R.id.confirmPassword);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,142);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri == null){
                    Toast.makeText(AddCoordinatorActivity.this, "Select image", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name.getText())){
                    name.setError("Enter Name");
                }else if(TextUtils.isEmpty(email.getText())){
                    email.setError("Enter Email");
                }else if(TextUtils.isEmpty(phone.getText())){
                    phone.setError("Enter Phone");
                }else if(TextUtils.isEmpty(password.getText())){
                    password.setError("Enter Password");
                }else if(TextUtils.isEmpty(confirpassword.getText())){
                    confirpassword.setError("Enter Confirm Password");
                }else if(!confirpassword.getText().toString().equals(password.getText().toString())){
                    confirpassword.setError("Password Not Match");
                    password.setError("Password Not Match");
                }else{
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // Get the data from an ImageView as bytes
                                mSelectImage.setDrawingCacheEnabled(true);
                                mSelectImage.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) mSelectImage.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("UsersImages").child(mAuth.getCurrentUser().getUid()+".jpeg");
                                UploadTask uploadTask = storageRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {@Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    User user = new User();
                                    user.setName(name.getText().toString());
                                    user.setPhone(phone.getText().toString());
                                    user.setEmail(email.getText().toString());
                                    user.setUID(mAuth.getCurrentUser().getUid());
                                    user.setUserType("Coordinator");
                                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AddCoordinatorActivity.this, "Coordinator Added Successfully", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(AddCoordinatorActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                });
                            }else{
                                Toast.makeText(AddCoordinatorActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
                        Uri uri;
                        @Override
                        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                            super.onActivityResult(requestCode, resultCode, data);
                            if(requestCode == 142 && resultCode == RESULT_OK){
                                uri = data.getData();
                                mSelectImage.setImageURI(uri);
                            }
                        }

                        @Override
                        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                            if(item.getItemId() == android.R.id.home){
                                finish();
                            }
                            return super.onOptionsItemSelected(item);
                        }
                    }