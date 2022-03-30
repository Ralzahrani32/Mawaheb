package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
    }
}