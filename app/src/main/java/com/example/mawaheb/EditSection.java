package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    }
}