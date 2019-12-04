package com.example.fellowtraveller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class Wallet extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FILE_NAME = "fellow_login_state.txt";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);



        Toolbar toolbar =  findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        closeDrawer();
        switch (item.getItemId()){
            case R.id.home:
                Intent c = new Intent(Wallet.this, MainHomeActivity.class);
                startActivity(c);
                finish();
                break;

            case R.id.profile:
                break;

            case R.id.wallet:
                Intent s = new Intent(Wallet.this, Wallet.class);
                finish();
                break;
            case R.id.settings:
                Intent i = new Intent(Wallet.this, Settings.class);
                startActivity(i);
                finish();
                break;
            case R.id.logout:
                Intent j = new Intent(Wallet.this, MainActivity.class);
                startActivity(j);
                finish();
                break;
        }
        return true;
    }
    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            super.onBackPressed();
    }
}
