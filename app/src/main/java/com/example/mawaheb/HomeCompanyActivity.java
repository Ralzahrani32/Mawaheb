package com.example.mawaheb;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mawaheb.NotificationFragment;
import com.example.mawaheb.R;
import com.example.mawaheb.SectionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_company);


        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new SectionFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.talents:
                        setTitle("Home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new SectionFragment()).commit();
                        break;

                    case R.id.notification:
                        setTitle("Notification");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new NotificationFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}