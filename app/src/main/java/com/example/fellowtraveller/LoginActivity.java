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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Button btn,btn_login;
    private JsonApi jsonPlaceHolderApi;
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
                if (CheckEmail() | CheckPassword()) {
                    createUser();
                }

            }
        });
    }
    private void createUser(){
        String email = textInputEmail.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();
        Call<Status_handling> call = jsonPlaceHolderApi.getUserAuth(email,password);

        call.enqueue(new Callback<Status_handling> () {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"response "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if(status.getStatus().equals("success")){
                    save("true");
                    Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(LoginActivity.this,"Ανεπιτυχής είσοδος",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
    private boolean CheckEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean CheckPassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
}