package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userType = sharedPreferences.getString("userType","");
        if(userType.equals("Coordinator")){
            delete.setVisibility(View.VISIBLE);
        }else if (userType.equals("User") && talent.getCreatorId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            delete.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Talent_Details_Activity2.this).setMessage("Do you want to delete this post?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Talents").child(talent.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Talent_Details_Activity2.this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(Talent_Details_Activity2.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
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

    }

    }
