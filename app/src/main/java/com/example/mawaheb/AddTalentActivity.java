package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddTalentActivity extends AppCompatActivity {
    private ImageView selectImage;
    private EditText title;
    private EditText details;
    private Spinner talents;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_talent);

        selectImage = findViewById(R.id.select_image);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        talents = findViewById(R.id.talents);
        add = findViewById(R.id.add);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,142);
            }
        });
    }
}