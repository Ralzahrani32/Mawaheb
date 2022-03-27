package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Talent_Details_Activity2 extends AppCompatActivity {
    private ImageView imageTalent;
    private TextView title;
    private TextView type;
    private TextView details;
    private Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_details2);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageTalent = findViewById(R.id.imageTalent);
        title = findViewById(R.id.title);
        type = findViewById(R.id.type);
        details = findViewById(R.id.details);
        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        Talent talent = (Talent) intent.getSerializableExtra("talent");
        title.setText(talent.getTitle());
        type.setText(talent.getType());
        details.setText(talent.getDetails());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("TalentsImages").child(talent.getId()+".jpeg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()  {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(Talent_Details_Activity2.this)
                        .load(uri)
                        .into(imageTalent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });



    }
}