package com.example.fellowtraveller;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String FILE_NAME = "fellow_login_state.txt";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView circleImageView,circleImageViewNav ;
    private ImageButton imageButtonUpload,imageButtonEdit,imageButtonAccept,imageButtonCancel;
    private Uri mImageUri;
    private TextView textViewAboutMe,tV_user_rating;
    private ImageView Img_friendly,Img_reliable,Img_careful,Img_consistent;
    private EditText editText;
    private Button readReviewsButton;
    private int id;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        tV_user_rating = findViewById(R.id.user_rating);
        Img_friendly = findViewById(R.id.profile_img_friendly);
        Img_reliable = findViewById(R.id.profile_img_reliable);
        Img_careful= findViewById(R.id.profile_img_careful);
        Img_consistent = findViewById(R.id.profile_img_consistent);


        Toolbar toolbar =  findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);
        circleImageView = findViewById(R.id.profile_picture);

        readReviewsButton = findViewById(R.id.profile_all_reviews_btn);

        imageButtonUpload = findViewById(R.id.profile_image_upload_button);
        imageButtonEdit = findViewById(R.id.profile_edit_button);
        imageButtonAccept = findViewById(R.id.profile_button_accept);
        imageButtonCancel = findViewById(R.id.profile_button_cancel);

        editText = (EditText) findViewById(R.id.profile_editText);
        textViewAboutMe = findViewById(R.id.profile_about_me);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



        navigationView.getMenu().getItem(1).setChecked(true);

        imageButtonAccept.setVisibility(View.GONE);
        imageButtonCancel.setVisibility(View.GONE);


        //loadImageFromStorage();


        imageButtonUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                onChooseFile(v);
            }
        });
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewAboutMe.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);

                imageButtonEdit.setVisibility(View.GONE);

                imageButtonAccept.setVisibility(View.VISIBLE);
                imageButtonCancel.setVisibility(View.VISIBLE);

                imageButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textViewAboutMe.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);

                        imageButtonEdit.setVisibility(View.VISIBLE);

                        imageButtonAccept.setVisibility(View.GONE);
                        imageButtonCancel.setVisibility(View.GONE);
                    }
                });

                imageButtonAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String aboutInfo = editText.getText().toString();
                        if(aboutInfo != null && !aboutInfo.isEmpty())
                        {
                            textViewAboutMe.setText(aboutInfo);
                        }

                        textViewAboutMe.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);

                        imageButtonEdit.setVisibility(View.VISIBLE);

                        imageButtonAccept.setVisibility(View.GONE);
                        imageButtonCancel.setVisibility(View.GONE);
                    }
                });
            }
        });

        readReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(Profile.this, ReviewsActivity.class);
                r.putExtra("Target_id",id);
                startActivity(r);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        loadUserInfoFromStoarge();
        LoadUserInfoFromServer();
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        closeDrawer();
        switch (item.getItemId()){
            case R.id.home:
                Intent c = new Intent(Profile.this, HomeBetaActivity.class);
                startActivity(c);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;

            case R.id.profile:
                break;

            case R.id.wallet:
                Intent s = new Intent(Profile.this, Wallet.class);
                startActivity(s);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;
            case R.id.settings:
                Intent k = new Intent(Profile.this, Settings.class);
                startActivity(k);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

                break;
            case R.id.logout:
                save("false");
                Intent j = new Intent(Profile.this, MainActivity.class);
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



    private void LoadUserInfoFromServer(){
        Log.i("Chriss","id : "+id);
        Call<RateUserContainerItem> call = jsonPlaceHolderApi.getUserInfo(id);
        call.enqueue(new Callback<RateUserContainerItem>() {
            @Override
            public void onResponse(Call<RateUserContainerItem> mcall, Response<RateUserContainerItem> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Profile.this,"response "+response.errorBody()+"\n"+"response "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                RateUserContainerItem container = response.body();
                tV_user_rating.setText(container.getUser().getRate()+"");
                SetImgToFriendly(container.getFriendly());
                SetImgToReliable(container.getReliable());
                SetImgToCareful(container.getCareful());
                SetImgToConsistent(container.getConsistent());

               /* Img_friendly = findViewById(R.id.profile_img_friendly);
                Img_reliable = findViewById(R.id.profile_img_reliable);
                Img_careful= findViewById(R.id.profile_img_careful);
                Img_consistent = findViewById(R.id.profile_img_consistent);*/

            }

            @Override
            public void onFailure(Call<RateUserContainerItem> call, Throwable t) {
               // Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void SetImgToFriendly(int pos){
        switch (pos){
            case 1:
                Img_friendly.setImageResource(R.drawable.ic_1_orange);
                break;
            case 2:
                Img_friendly.setImageResource(R.drawable.ic_2_orange);
                break;
            case 3:
                Img_friendly.setImageResource(R.drawable.ic_3_orange);
                break;
            case 4:
                Img_friendly.setImageResource(R.drawable.ic_4_orange);
                break;
            case 5:
                Img_friendly.setImageResource(R.drawable.ic_5_orange);
                break;
            default:
                break;
        }
    }
    public void SetImgToReliable(int pos){
        switch (pos){
            case 1:
                Img_reliable.setImageResource(R.drawable.ic_1_orange);
                break;
            case 2:
                Img_reliable.setImageResource(R.drawable.ic_2_orange);
                break;
            case 3:
                Img_reliable.setImageResource(R.drawable.ic_3_orange);
                break;
            case 4:
                Img_reliable.setImageResource(R.drawable.ic_4_orange);
                break;
            case 5:
                Img_reliable.setImageResource(R.drawable.ic_5_orange);
                break;
            default:
                break;
        }
    }
    public void SetImgToCareful(int pos){
        switch (pos){
            case 1:
                Img_careful.setImageResource(R.drawable.ic_1_orange);
                break;
            case 2:
                Img_careful.setImageResource(R.drawable.ic_2_orange);
                break;
            case 3:
                Img_careful.setImageResource(R.drawable.ic_3_orange);
                break;
            case 4:
                Img_careful.setImageResource(R.drawable.ic_4_orange);
                break;
            case 5:
                Img_careful.setImageResource(R.drawable.ic_5_orange);
                break;
            default:
                break;
        }
    }
    public void SetImgToConsistent(int pos){
        switch (pos){
            case 1:
                Img_consistent.setImageResource(R.drawable.ic_1_orange);
                break;
            case 2:
                Img_consistent.setImageResource(R.drawable.ic_2_orange);
                break;
            case 3:
                Img_consistent.setImageResource(R.drawable.ic_3_orange);
                break;
            case 4:
                Img_consistent.setImageResource(R.drawable.ic_4_orange);
                break;
            case 5:
                Img_consistent.setImageResource(R.drawable.ic_5_orange);
                break;
            default:
                break;
        }
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

    public void loadUserInfoFromStoarge() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            View header = navigationView.getHeaderView(0);
            TextView name = header.findViewById(R.id.user_name_drawer);
            TextView email = header.findViewById(R.id.user_email_drawer);
            TextView name2 = findViewById(R.id.profile_name);
            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i==2){
                    name.setText(text);
                    name2.setText(text);
                }else if(i==3){
                    email.setText(text);
                }else if(i==1){
                    id = Integer.parseInt(text);
                    Log.i("Chriss","id: "+id);

                }
                i++;
            }
            //String t = "name : "+name.getText()+"\n"+"email: "+email.getText()+"\n"+"id : "+id;
            //Toast.makeText(HomeBetaActivity.this,"id : "+id, Toast.LENGTH_SHORT).show();
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

    private void loadImageFromStorage()
    {
        circleImageViewNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
        circleImageView = findViewById(R.id.profile_picture);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        try {
            File f = new File(directory, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            circleImageView.setImageBitmap(b);
            circleImageViewNav.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    public void onChooseFile(View v){
        CropImage.activity().start(Profile.this);


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
                Bitmap bit = result.getBitmap();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    Log.i("Chris","Bit map "+bitmap.toString());
                    saveToInternalStorage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                 saveToInternalStorage(result.getBitmap());
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Log.i("error",e.getMessage());

            }
        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File( directory,"profile.jpg");
        Log.i("Chris","path : "+ path.getPath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }



}