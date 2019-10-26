package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Button btn,register_user;
    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        textInputName = findViewById(R.id.register_editText_name);
        textInputEmail = findViewById(R.id.register_editText_email);
        textInputPassword = findViewById(R.id.register_editText_password);
        register_user = findViewById(R.id.register_button_register);
        btn = findViewById(R.id.register_button_login);




        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(mainIntent);
            }
        });






        register_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!CheckEmail() | !CheckName() | !CheckPassword()) {

                }
                String input = "Email: " + textInputEmail.getEditText().getText().toString();
                input += "\n";
                input += "Username: " + textInputName.getEditText().getText().toString();
                input += "\n";
                input += "Password: " + textInputPassword.getEditText().getText().toString();

              //  Toast.makeText(RegisterActivity.this, input, Toast.LENGTH_SHORT).show();
                createUser();
            }
        });























    }

    private void createUser(){
        String name = textInputName.getEditText().getText().toString();
        String email = textInputEmail.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();

        User user = new User(1,name,email,password);

        Call<User> call = jsonPlaceHolderApi.createUser(name,email,password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Error", Toast.LENGTH_LONG);
                }
                User postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getName() + "\n";
                content += "Title: " + postResponse.getEmail() + "\n";
                content += "Text: " + postResponse.getPassword() + "\n\n";
                Toast.makeText(RegisterActivity.this,content, Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(), Toast.LENGTH_LONG);
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

    private boolean CheckName() {
        String usernameInput = textInputName.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputName.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputName.setError(null);
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
