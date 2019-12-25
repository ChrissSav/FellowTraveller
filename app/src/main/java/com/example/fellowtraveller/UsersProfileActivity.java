package com.example.fellowtraveller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersProfileActivity extends AppCompatActivity {
    private CircleImageView circleImageView,circleImageViewNav ;
    private TextView textViewAboutMe;
    private Button readReviewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);


        circleImageView = findViewById(R.id.profile_picture);

        readReviewsButton = findViewById(R.id.profile_all_reviews_btn);




        textViewAboutMe = findViewById(R.id.profile_about_me);
        readReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(UsersProfileActivity.this, ReviewsActivity.class);
                startActivity(r);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });






    }
}
