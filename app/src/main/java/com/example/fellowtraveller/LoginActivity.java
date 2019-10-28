package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
yourEditText.setTransformationMethod(new PasswordTransformationMethod());
*/

public class LoginActivity extends AppCompatActivity {
    private Button btn,btn_login;
    private JsonApi jsonPlaceHolderApi;
    private EditText password;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        setContentView(R.layout.activity_login);
        textInputEmail = findViewById(R.id.login_editText_email);
        textInputPassword = findViewById(R.id.login_editText_password);
        btn = findViewById(R.id.login_button_register);
        btn_login = findViewById(R.id.login_button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(mainIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CheckEmail() & CheckPassword()) {
                    createUser();
                }

            }
        });
    }


    private void createUser() {
        String email = textInputEmail.getEditText().getText().toString();
        Call<List<User>> call = jsonPlaceHolderApi.getUserById(email);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                   // Toast.makeText(LoginActivity.this,"Return object = "+response.body().get(0).getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainHomeActivity.class);
                    intent.putExtra("user",response.body().get(0));
                    startActivity(intent);
                    finish();
                }
                else {
                   Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }




    private boolean CheckEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean CheckPassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
}
