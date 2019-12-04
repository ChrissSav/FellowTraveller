package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
<<<<<<< Updated upstream
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainHomeActivity extends AppCompatActivity {
    private Dialog dia;
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Button btn_popup_menu,btn2;
    private TextView t ;
=======
import com.google.android.material.navigation.NavigationView;

public class MainHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   // private Dialog dia;
   // private Button btn_popup_menu,btn2;

    DrawerLayout drawerLayout;
    //TextView tvInfo;

>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);


        Toolbar toolbar = (Toolbar) findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);

       // tvInfo = (TextView) findViewById(R.id.tv_info);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        //navigationView.setNavigationItemSelectedListener (this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.home_bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        t = findViewById(R.id.home_textView3);
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







        t.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                save("false");
                Intent intent = new Intent(MainHomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



       // Intent intent = getIntent();
       // User user = intent.getParcelableExtra("user");
       // Toast.makeText(MainHomeActivity.this, "Name = "+user.getName()+"\n"+"Email = "+user.getEmail()+"\n"+"Password = "+user.getPassword()+"\n", Toast.LENGTH_SHORT).show();
    }

<<<<<<< Updated upstream
    public void save(String status) {
        String text = status;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
           // Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
=======
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String itemName = (String) item.getTitle();
        //tvInfo.setText(itemName);

        closeDrawer();
        switch (item.getItemId()){
            case R.id.profile:
                break;
            case R.id.wallet:
                break;
            case R.id.settings:
                break;
            case R.id.logout:
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
>>>>>>> Stashed changes
    }
}
