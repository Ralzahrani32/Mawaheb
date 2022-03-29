package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    }
}