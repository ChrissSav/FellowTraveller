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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_beta);



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
        loadUserInfo();
        // loadImageFromStorage();
        BottonNav();
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        /*float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        Toast.makeText(HomeBetaActivity.this,"dpWidth : "+dpWidth, Toast.LENGTH_SHORT).show();*/


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
                Intent s = new Intent(HomeBetaActivity.this, Wallet.class);
                startActivity(s);
                finish();
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

    public void loadUserInfo() {
        LoadUserPic();
        FileInputStream fis = null;
        try {
            fis = openFileInput(getString(R.string.FILE_USER_INFO));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            View header = navigationView.getHeaderView(0);
            TextView name = header.findViewById(R.id.user_name_drawer);
            TextView email = header.findViewById(R.id.user_email_drawer);
            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i==2){
                    name.setText(text);
                }else if(i==3){
                    email.setText(text);
                }else if(i==1){
                    id = Integer.parseInt(text);
                    Log.i("NotificationDev","id: "+id);
                    CheckUserInfoToUpdate(id);
                }
                i++;
            }
            //String t = "name : "+name.getText()+"\n"+"email: "+email.getText()+"\n"+"id : "+id;
           // Toast.makeText(HomeBetaActivity.this,"id : "+id, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void LoadUserPic() {
        circleImageViewNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void ... params ) {
                FileInputStream fis = null;
                try {
                    fis = openFileInput(getString(R.string.FILE_USER_PICTURE));
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String line,line1 = "";
                    try
                    {
                        while ((line = br.readLine()) != null)
                            line1+=line;
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return line1;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return "null";
            }

            @Override
            protected void onPostExecute( String result ) {
                // continue what you are doing...
                if(!result.equals("null")) {
                    circleImageViewNav.setImageBitmap(StringToBitMap(result));
                }

            }
        }.execute();
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

    private void CheckUserInfoToUpdate(int id){
        JsonApi jsonPlaceHolderApi;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        Call<UserAuth> call = jsonPlaceHolderApi.CheckUserInfo(id);

        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> mcall, Response<UserAuth> response) {
                if (!response.isSuccessful()) {
                   // Toast.makeText(MainActivity.this,"response "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                UserAuth user = response.body();
                if(user.getName()!=null){
                    //save("true",user.getId()+"",user.getName(),user.getEmail());
                    SaveUserPicture(user.getPicture());
                    LoadUserPic();
                    return;
                }
               // Toast.makeText(MainActivity.this,"Ανεπιτυχής είσοδος",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                //  Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void SaveUserPicture(String image) {
        String text = image;
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
}
