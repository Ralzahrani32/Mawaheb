package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_comapny);


        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new SectionsFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.talents:
                        setTitle("Home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new SectionsFragment()).commit();
                        break;

                    case R.id.notification:
                        setTitle("Notification");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new NotificationFragment()).commit();

                        break;
                    case R.id.profile:
                        setTitle("Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ProfileCompanyFragment()).commit();

                        break;
                }
                return true;
            }
        });
    }
}