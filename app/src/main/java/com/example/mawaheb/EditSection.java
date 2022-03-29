package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
    }
}