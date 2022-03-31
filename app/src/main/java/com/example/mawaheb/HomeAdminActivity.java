package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        setTitle("Manage Coordinator");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ManageCoordinatorFragment()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ManageCoordinator:
                        setTitle("Manage Coordinator");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ManageCoordinatorFragment()).commit();
                        break;

                    case R.id.ManageSections:
                        setTitle("Manage Sections");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ManageSectionsFragment()).commit();

                        break;
                        
                    case R.id.ManageUsers:
                        setTitle("Manage Users");
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ManageUsersFragment()).commit();

                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            new AlertDialog.Builder(this).setMessage("Do you want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(HomeAdminActivity.this,loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
