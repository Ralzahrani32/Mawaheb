package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeUserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_main);
        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new SectionFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        setTitle("Home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new SectionFragment()).commit();
                        break;

                    case R.id.search:
                        setTitle("Search");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new fragment_search()).commit();

                        break;
                    case R.id.notification:
                        setTitle("Notification");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new NotificationFragment()).commit();

                        break;
                    case R.id.profile:
                        setTitle("Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ProfileFragment()).commit();

                        break;
                }
                return true;
            }
        });

    }
}