package com.example.fellowtraveller;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private int id;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView circleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);




        Toolbar toolbar =  findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        navigationView.getMenu().getItem(3).setChecked(true);
        loadUserInfo();
      //  loadImageFromStorage();
    
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        closeDrawer();
        switch (item.getItemId()){
            case R.id.home:
                Intent c = new Intent(Settings.this, HomeBetaActivity.class);
                startActivity(c);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.profile:
                Intent a = new Intent(Settings.this, Profile.class);
                startActivity(a);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.wallet:
                Intent s = new Intent(Settings.this, Wallet.class);
                startActivity(s);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;
            case R.id.settings:

                break;
            case R.id.logout:
                save("false");
                Intent j = new Intent(Settings.this, MainActivity.class);
                startActivity(j);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
    public void save(String status) {
        String text = status;
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
                    //id = Integer.parseInt(text);
                }
                i++;
            }
            //String t = "name : "+name.getText()+"\n"+"email: "+email.getText()+"\n"+"id : "+id;
            //Toast.makeText(MainHomeActivity.this,t,Toast.LENGTH_SHORT).show();
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
    private void LoadUserPic(){
        circleImageView = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
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
                return "null";
            }

            @Override
            protected void onPostExecute( String result ) {
                // continue what you are doing...
                if(result!="null") {
                    circleImageView.setImageBitmap(StringToBitMap(result));
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

}
