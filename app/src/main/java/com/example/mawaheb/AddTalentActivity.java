package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddTalentActivity extends AppCompatActivity {
    private ImageView selectImage;
    private EditText title;
    private EditText details;
    private Spinner talents;
    private Button add;

    ArrayList<Section> sections = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_talent);
        getSupportActionBar().setTitle("Add Talent");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectImage = findViewById(R.id.select_image);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        talents = findViewById(R.id.talents);
        add = findViewById(R.id.add);

        ArrayAdapter<Section> arrayAdapter = new ArrayAdapter<Section>(this, android.R.layout.simple_spinner_dropdown_item,sections);

        talents.setAdapter(arrayAdapter);

        FirebaseDatabase.getInstance().getReference("Sections").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sections.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Section section = dataSnapshot.getValue(Section.class);
                    sections.add(section);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}