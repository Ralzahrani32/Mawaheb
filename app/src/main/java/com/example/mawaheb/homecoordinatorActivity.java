package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homecoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homecoordinator);
        setTitle("Talents");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new TalentsFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        setTitle("Talents");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new TalentsFragment()).commit();
                        break;
                    case R.id.ManageSections:
                        setTitle("Sections");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ManageSectionsFragment()).commit();
                        break;
                    case R.id.inquiries:
                        setTitle("Inquiries");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ManageInquiriesFragment()).commit();

                        break;
                }
                return true;
            }
        });
    }

    }
