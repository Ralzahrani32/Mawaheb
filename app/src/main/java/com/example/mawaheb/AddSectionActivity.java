package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

    }
}