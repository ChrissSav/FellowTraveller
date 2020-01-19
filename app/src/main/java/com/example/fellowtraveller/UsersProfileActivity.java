package com.example.fellowtraveller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersProfileActivity extends AppCompatActivity {
    private CircleImageView circleImageView,circleImageViewNav ;
    private TextView textViewAboutMe,rate,name;
    private Button readReviewsButton;
    private int id;
    private ImageView Img_friendly,Img_reliable,Img_careful,Img_consistent;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);
        final Intent intent = getIntent();
        id = intent.getIntExtra("User_id",0);
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);

        circleImageView = findViewById(R.id.UsersProfile_profile_picture);
        readReviewsButton = findViewById(R.id.profile_all_reviews_btn);
        rate = findViewById(R.id.UserProfile_user_rating);
        Img_friendly = findViewById(R.id.UserProfile_img_friendly);
        Img_reliable = findViewById(R.id.UserProfile_img_reliable);
        Img_careful= findViewById(R.id.UserProfile_img_careful);
        Img_consistent = findViewById(R.id.UserProfile_img_consistent);
        name  = findViewById(R.id.UserProfile_tv_name);

        textViewAboutMe = findViewById(R.id.profile_about_me);
        readReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(UsersProfileActivity.this, ReviewsActivity.class);
                r.putExtra("Target_id",id);
                startActivity(r);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        LoadUserInfoFromServer(id);




    }


    private void LoadUserInfoFromServer(int id){
        Call<RateUserContainerItem> call = jsonPlaceHolderApi.getUserInfo(id);
        call.enqueue(new Callback<RateUserContainerItem>() {
            @Override
            public void onResponse(Call<RateUserContainerItem> mcall, Response<RateUserContainerItem> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UsersProfileActivity.this,"response "+response.errorBody()+"\n"+"response "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                RateUserContainerItem container = response.body();
                rate.setText(container.getUser().getRate()+"");
                name.setText(container.getUser().getName());
                SetImgToFriendly(container.getFriendly());
                SetImgToReliable(container.getReliable());
                SetImgToCareful(container.getCareful());
                SetImgToConsistent(container.getConsistent());
                if(!container.getUser().getPicture().equals("null")){
                    circleImageView.setImageBitmap(StringToBitMap(container.getUser().getPicture()));
                }

            }

            @Override
            public void onFailure(Call<RateUserContainerItem> call, Throwable t) {
                // Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetImgToFriendly(int pos){
        Log.i("SetImgToFriendly","pos "+pos);
        switch (pos){
            case 0:
                Img_friendly.setImageResource(R.drawable.ic_0_orange);
                break;
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
            case 0:
                Img_reliable.setImageResource(R.drawable.ic_0_orange);
                break;
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
            case 0:
                Img_careful.setImageResource(R.drawable.ic_0_orange);
                break;
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
            case 0:
                Img_consistent.setImageResource(R.drawable.ic_0_orange);
                break;
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
