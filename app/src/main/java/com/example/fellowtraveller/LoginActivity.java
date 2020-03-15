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
    private Button btn,btn_login;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalClass = (GlobalClass) getApplicationContext();
        //Log.i("frfrfrf",getString(R.string.api_url));
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api_url)).addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        setContentView(R.layout.activity_login);
        textInputEmail = findViewById(R.id.login_editText_email);
        textInputPassword = findViewById(R.id.login_editText_password);
        btn = findViewById(R.id.login_button_register);
        btn_login = findViewById(R.id.login_button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent mainIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                String str = textInputEmail.getEditText().getText().toString();

                mainIntent.putExtra( "USER_NAME",str);
                startActivity(mainIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CheckEmail() | CheckPassword()) {
                    LoginUser();
                }

            }
        });
    }
    private void LoginUser(){
        final String email = textInputEmail.getEditText().getText().toString();
        final String password = textInputPassword.getEditText().getText().toString();
        Call<UserAuth> call = jsonPlaceHolderApi.getUserAuth(email,password);

        call.enqueue(new Callback<UserAuth> () {
            @Override
            public void onResponse(Call<UserAuth> mcall, Response<UserAuth> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"response "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                UserAuth user = response.body();
                if(user.getName()!=null){
                    save("true",user.getId()+"",user.getName(),user.getEmail());
                    globalClass.setId(user.getId());
                    globalClass.setName(user.getName());
                    globalClass.setEmail(user.getEmail());
                    globalClass.setUser_icon(user.getPicture());
                    SaveUserPicture(user.getPicture());
                    Intent intent = new Intent(LoginActivity.this, HomeBetaActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(LoginActivity.this,"Ανεπιτυχής είσοδος",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void save(String status,String id,String name,String email) {
        String text = status+"\n"+id+"\n"+name+"\n"+email;
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