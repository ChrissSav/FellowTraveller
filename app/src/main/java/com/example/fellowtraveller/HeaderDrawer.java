package com.example.fellowtraveller;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HeaderDrawer extends AppCompatActivity {
    private  User user;
    private String Email;

    public HeaderDrawer(String email) {

        this.Email = email;




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_drawer);

        TextView userEmailTV = (TextView)findViewById(R.id.user_email_drawer);
        userEmailTV.setText(Email);

    }

}
