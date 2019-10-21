package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private Button btn;
    private boolean passwordvisible = false;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*password = findViewById(R.id.login_editText_password);
        btnPassword = findViewById(R.id.login_button_visible);*/
        btn = findViewById(R.id.register_button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(mainIntent);
            }
        });

/*
        btnPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(passwordvisible){
                    btnPassword.setBackgroundResource(R.drawable.login_password_view);
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    passwordvisible=false;
                }else{
                    passwordvisible=true;
                    btnPassword.setBackgroundResource(R.drawable.login_password_hide);
                    password.setTransformationMethod(null);
                }


            }
        });*/

    }
}
