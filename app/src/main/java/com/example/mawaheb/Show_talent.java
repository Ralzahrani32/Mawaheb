package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Show_talent extends AppCompatActivity {
    ArrayList<Talent> talents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_talent);
        getSupportActionBar().setTitle("Talents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AddTalentActivity.class);
                startActivity(intent);
        }
        });
        Intent intent = getIntent();
        Section section = (Section) intent.getSerializableExtra("section");
    }
}