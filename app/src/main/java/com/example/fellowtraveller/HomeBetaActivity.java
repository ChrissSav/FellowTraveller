package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeBetaActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int id;
    private BottomNavigationView bottomNavigationView;
    private CircleImageView circleImageViewNav;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_beta);
        globalClass = (GlobalClass) getApplicationContext();



        mTabLayout = findViewById(R.id.homeBetaActivity_container_main_tabs);
        Log.i("ixi","ixi :"+mTabLayout.toString());
        mViewPager = findViewById(R.id.home_container_main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        tab.select();
        mViewPager.setOffscreenPageLimit(4);

        Toolbar toolbar =  findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.getMenu().getItem(0).setChecked(true);

        bottomNavigationView = findViewById(R.id.homeBetaActivity_Bottom_Nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);

        LoadUserAllInfo();

        BottonNav();
        CheckUserNotification(globalClass.getId());

    }

    public void BottonNav(){

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.bottom_nav_chat:
                                Intent b = new Intent(HomeBetaActivity.this, Chat.class);
                                startActivity(b);
                                finish();
                                break;
                            case R.id.bottom_nav_home:
                                TabLayout.Tab tab = mTabLayout.getTabAt(1);
                                tab.select();
                                break;
                            case R.id.bottom_nav_notification:
                                Intent a = new Intent(HomeBetaActivity.this, NotificationActivity.class);
                                startActivity(a);
                                overridePendingTransition(0,0);
                                finish();
                                break;
                        }
                        return true;
                    }
                });


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        closeDrawer();
        switch (item.getItemId()){
            case R.id.home:
                break;
            case R.id.profile:
                Intent a = new Intent(HomeBetaActivity.this, Profile.class);
                startActivity(a);
                finish();
                break;
            case R.id.wallet:
               /* Intent s = new Intent(HomeBetaActivity.this, Wallet.class);
                startActivity(s);
                finish();*/
                break;
            case R.id.settings:
                Intent i = new Intent(HomeBetaActivity.this, Settings.class);
                startActivity(i);
                finish();
                break;
            case R.id.logout:
                save();
                Intent j = new Intent(HomeBetaActivity.this, MainActivity.class);
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

    public void save() {
        DeleteUserPicture();
        String text = "false";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.FILE_USER_INFO), MODE_PRIVATE);
            fos.write(text.getBytes());
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
    }


    public void DeleteUserPicture() {
        String text = "null";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.FILE_USER_PICTURE), MODE_PRIVATE);
            fos.write(text.getBytes());
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
    }



    public  void LoadUserAllInfo(){
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.user_name_drawer);
        TextView email = header.findViewById(R.id.user_email_drawer);
        name.setText(globalClass.getName());
        email.setText(globalClass.getEmail());
        if(!globalClass.getUser_icon().equals("null")){
            circleImageViewNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
            circleImageViewNav.setImageBitmap(StringToBitMap(globalClass.getUser_icon()));
        }

    }
    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }



    private void CheckUserNotification(int id){
        JsonApi jsonPlaceHolderApi;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api_url)).addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        Call<Status_handling> call = jsonPlaceHolderApi.getNotificationOfUserCount(id);

        call.enqueue(new Callback<Status_handling>() {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    // Toast.makeText(MainActivity.this,"response "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if(!status.getMsg().equals("0")){
                    UpdateNotificationCount(status.getMsg());
                    return;
                }
                // Toast.makeText(MainActivity.this,"Ανεπιτυχής είσοδος",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                //  Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void UpdateNotificationCount(String count){
        BottomNavigationMenuView mBottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        View view = mBottomNavigationMenuView.getChildAt(2);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_icon_icon,
                        mBottomNavigationMenuView, false);

        TextView textView = cart_badge.findViewById(R.id.notification_counter);
        textView.setText(count);
        itemView.addView(cart_badge);
    }
}

