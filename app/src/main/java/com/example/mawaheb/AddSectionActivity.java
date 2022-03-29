package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.parser.Section;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddSectionActivity extends AppCompatActivity {
    private ImageView selectImage;
    private EditText title;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);
        getSupportActionBar().setTitle("Add Section");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectImage = findViewById(R.id.selectImage);
        title = findViewById(R.id.title);
        add = findViewById(R.id.add);
        selectImage.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(AddSectionActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
                }else if(title.getText().toString().isEmpty()){
                    title.setError("Enter title");
                }else{
                    String id = FirebaseDatabase.getInstance().getReference("Sections").push().getKey();
                    Section section = new Section();
                    section.setTitle(title.getText().toString());
                    section.setId(id);
                    selectImage.setDrawingCacheEnabled(true);
                    selectImage.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) selectImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ((Bitmap) bitmap).compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("SectionsImages").child(id+".jpeg");
                    UploadTask uploadTask = storageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseDatabase.getInstance().getReference("Sections").child(id).setValue(section).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddSectionActivity.this, "Section Added Successfully", Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(AddSectionActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
                    selectImage.setImageURI(uri);
                }
            }
            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem  item) {
                android.view.MenuItem item;
                if(item.getItemId() == android.R.id.home){
                    finish();
                }
                return super.onOptionsItemSelected(item);
            }
        }