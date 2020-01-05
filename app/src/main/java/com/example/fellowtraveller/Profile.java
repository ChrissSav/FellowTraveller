package com.example.fellowtraveller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView circleImageView, circleImageViewNav;
    private ImageButton imageButtonUpload, imageButtonEdit, imageButtonAccept, imageButtonCancel;
    private Uri mImageUri;
    private TextView textViewAboutMe;
    private EditText editText;
    private Button readReviewsButton;
    private String id;

    private DatabaseReference userDatabase;
    //Firabase Storage Profile Image
    private StorageReference mImageStorage;
    //Progress Dialog
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        Toolbar toolbar = findViewById(R.id.home_appBar);
        setSupportActionBar(toolbar);

        id = getId();
        userDatabase = FirebaseDatabase.getInstance().getReference();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        circleImageView = (CircleImageView) findViewById(R.id.profile_picture);

        id = getId();
        mImageStorage = FirebaseStorage.getInstance().getReference();

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


        userDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.child("Users").child(id).child("image").getValue(String.class);



                   // Picasso.get().load(image).placeholder(R.drawable.cylinder).error(R.drawable.profile_pic).into(circleImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadUserInfo();


        imageButtonUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                        if (aboutInfo != null && !aboutInfo.isEmpty()) {
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
                startActivity(r);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        closeDrawer();
        switch (item.getItemId()) {
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


    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
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
            TextView name2 = findViewById(R.id.profile_name);
            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i == 2) {
                    name.setText(text);
                    name2.setText(text);
                } else if (i == 3) {
                    email.setText(text);
                } else if (i == 1) {
                    // id = Integer.parseInt(text);
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


    public void onChooseFile(View v) {
        CropImage.activity().start(Profile.this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(Profile.this);
                mProgressDialog.setTitle("Uploading Image..");
                mProgressDialog.setMessage("Please wait while we process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                mImageUri = result.getUri();

                File thumb_filePath = new File(result.getUri().getPath());


                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] thumb_byte = baos.toByteArray();

                UploadUserPic(thumb_byte);
                //FireBase Image Storage
                StorageReference filepath = mImageStorage.child("profile_images").child(id + ".jpg");
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloaduri = taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    mProgressDialog.dismiss();
                                    Uri download = task.getResult();
                                    userDatabase.child("Users").child(id).child("image").setValue(download.toString());
                                } else {

                                }
                            }
                        });
                    }
                });
            }
            //Finished Image Storage

            // Bitmap bit = result.getBitmap();
            // try {
            //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
            //Log.i("Chris","Bit map "+bitmap.toString());
            //saveToInternalStorage(bitmap);
            //  } catch (IOException e) {
            //e.printStackTrace();
            //}


            //saveToInternalStorage(result.getBitmap());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            // Exception e = result.getError();
            //Log.i("error",e.getMessage());

        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(directory, "profile.jpg");
        Log.i("Chris", "path : " + path.getPath());
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

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


    public String getId() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(getString(R.string.FILE_USER_INFO));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            String id = "-1";

            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i == 1) {
                    id = text;
                    return id;

                }
                i++;
            }
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
        return id;


    }

    public void UploadUserPic(byte[] thumb_byte){
        JsonApi jsonPlaceHolderApi;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        final String teemp = Base64.encodeToString(thumb_byte, Base64.DEFAULT);

        JsonObject jsonObject = new JsonObject();
        getId();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("icon", teemp);
        Call<Status_handling> call = jsonPlaceHolderApi.uploadImage(jsonObject);

        call.enqueue(new Callback<Status_handling>() {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Status_handling st = response.body();
                    Toast.makeText(Profile.this,"msg "+st.getMsg(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if(status.getStatus().equals("success")){
                    //   Toast.makeText(MainActivity.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    //  img2.setImageBitmap(StringToBitMap(status.getMsg()));
                    Log.i("SaveUserPicture","status.getStatus() = "+status.getStatus());
                    SaveUserPicture(status.getMsg());
                    LoadUserPic();
                    return;
                }else{
                    Toast.makeText(Profile.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                Toast.makeText(Profile.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public Bitmap StringToBitMap(String image){
        try{

            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            Log.i("BtimapSize","Size : "+encodeByte.length/1024 );
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);

            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public void LoadUserPic(){
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
                    circleImageViewNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
                    circleImageViewNav.setImageBitmap(StringToBitMap(result));
                }

            }
        }.execute();

    }

    public void LoadUserPic2() {

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
            circleImageView.setImageBitmap(StringToBitMap(line1));
            circleImageViewNav = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
            circleImageViewNav.setImageBitmap(StringToBitMap(line1));
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

    public void SaveUserPicture(String image) {
        String text = image;
        FileOutputStream fos = null;
        Log.i("SaveUserPicture","getString(R.string.FILE_USER_PICTURE) "+getString(R.string.FILE_USER_PICTURE));

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



    public String BitMapToString (Bitmap bitmap){
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }



}

