package com.example.fellowtraveller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;


public class Wallet extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FILE_NAME = "fellow_login_state.txt";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imgBtn1, imgBtn2;
    private ImageView imgView;
    private Uri mImageUri;
    private CircleImageView circleImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);



        Toolbar toolbar =  findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView =  findViewById(R.id.nav_view_wallet);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();


        imgView = findViewById(R.id.imageView2);
        imgBtn2 = (ImageButton) findViewById(R.id.imageButton3);
        circleImageView = findViewById(R.id.user_pic_new);

        imgBtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                onChooseFile(v);
            }
        });

        navigationView.getMenu().getItem(2).setChecked(true);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        closeDrawer();
        switch (item.getItemId()){
            case R.id.home:
                Intent c = new Intent(Wallet.this, MainHomeActivity.class);
                startActivity(c);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.profile:
                Intent a = new Intent(Wallet.this, Profile.class);
                startActivity(a);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.wallet:


                break;
            case R.id.settings:
                Intent i = new Intent(Wallet.this, Settings.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;
            case R.id.logout:
                save("false");
                Intent j = new Intent(Wallet.this, MainActivity.class);
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
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
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

    public void onChooseFile(View v){
        CropImage.activity().start(Wallet.this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {

             CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                mImageUri = result.getUri();
                circleImageView.setImageURI(mImageUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();

            }
        }
    }



}
