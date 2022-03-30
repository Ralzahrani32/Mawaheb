package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EditSection extends AppCompatActivity {

    private ImageView selectImage;
    private EditText title;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);
            getSupportActionBar().setTitle("Edit Section");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            selectImage = findViewById(R.id.selectImage);
            title = findViewById(R.id.title);
            update = findViewById(R.id.update);
            selectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 142);
                }
            });
            Intent intent = getIntent();

        Section section = (Section) intent.getSerializableExtra("section");
        title.setText(section.getTitle());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("SectionsImages").child(section.getId()+".jpeg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(EditSection.this)
                        .load(uri)
                        .into(selectImage);
                uri_select = uri;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri_select == null) {
                    Toast.makeText(EditSection.this, "Select Image", Toast.LENGTH_SHORT).show();
                } else if (title.getText().toString().isEmpty()) {
                    title.setError("Enter title");
                } else {
                    section.setTitle(title.getText().toString());
                    selectImage.setDrawingCacheEnabled(true);
                    selectImage.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) selectImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("SectionsImages").child(section.getId() + ".jpeg");
                    UploadTask uploadTask = storageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseDatabase.getInstance().getReference("Sections").child(section.getId()).setValue(section).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditSection.this, "Section Edited Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditSection.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }
}