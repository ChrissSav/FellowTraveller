package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainHomeActivity extends AppCompatActivity {
    private Dialog dia;
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;

    private Button btn_popup_menu,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this, myDrawer, R.string.open, R.string.close);

        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



       // dia = new Dialog(this);
        //dia.setContentView(R.layout.popup);
        //dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


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

        //btn_popup_menu = findViewById(R.id.home_button_popup_menu);
        //btn_popup_menu.setOnClickListener(new View.OnClickListener() {
           // public void onClick(View v)
            //{
             //   dia.show();
           // }
       // });
      //  btn2 = dia.findViewById(R.id.popup_button_close);
       // btn2.setOnClickListener(new View.OnClickListener() {
          //  public void onClick(View v)
           // {
            //    dia.dismiss();
          //  }
        //});

       // Intent intent = getIntent();
       // User user = intent.getParcelableExtra("user");
       // Toast.makeText(MainHomeActivity.this, "Name = "+user.getName()+"\n"+"Email = "+user.getEmail()+"\n"+"Password = "+user.getPassword()+"\n", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(myToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
