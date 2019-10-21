package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.home_bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeContainerFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()){
                            case R.id.bottom_nav_chat:
                                selectedFragment = new ChatContainerFragment();
                                break;
                            case R.id.bottom_nav_home:
                                selectedFragment = new HomeContainerFragment();
                                break;
                            case R.id.bottom_nav_notification:
                                selectedFragment = new NotificationContainerFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                        return true;
                    }
                });
    }
}
