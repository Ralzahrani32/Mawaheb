package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class ShowTalentsActivity extends AppCompatActivity {
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

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String userType=sharedPreferences.getString("userType","");
        if(!userType.equals("User")){
            floatingActionButton.hide();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        TalentAdapter adapter = new TalentAdapter(this,talents);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Talents").orderByChild("type").equalTo(section.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                talents.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Talent talent = dataSnapshot.getValue(Talent.class);
                    talents.add(talent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}