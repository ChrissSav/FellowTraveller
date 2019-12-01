package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Button button_login,button_register,button_back;
    private TextInputEditText textInputName;
    private TextInputEditText textInputEmail;
    private TextInputEditText textInputPassword;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TextInputEditText textInputBirthday;
    private TextInputEditText textInputPhone;
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

        textInputName = findViewById(R.id.RegisterActivity_editText_name);
        textInputEmail = findViewById(R.id.RegisterActivity_editText_email);
        textInputBirthday = findViewById(R.id.RegisterActivity_editText_date);
        textInputPhone = findViewById(R.id.RegisterActivity_editText_phone);
        textInputPassword = findViewById(R.id.RegisterActivity_editText_password);

        button_back = findViewById(R.id.RegisterActivity_button_back);
        button_register = findViewById(R.id.RegisterActivity_button_register);
        button_login = findViewById(R.id.RegisterActivity_button_login);



        textInputBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mon,d;
                if (month<=9){
                    mon = "0"+month;
                }
                else{
                    mon = month+"";
                }
                if(day<=9){
                    d = "0"+day;
                }
                else{
                    d = day+"";
                }
                String date = d + "/" + mon + "/" + year;
                textInputBirthday.setText(date+"");
            }
        };

        button_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(mainIntent);
            }
        });



        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        button_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CheckEmail() | CheckName() | CheckPassword()) {
                    createUser();
                }
            }
        });
    }


    public void OutFocus(){
        textInputName.clearFocus();
        textInputEmail.clearFocus();
        textInputPhone.clearFocus();
        textInputPassword.clearFocus();
    }

    private void createUser(){
        String name = textInputName.getText().toString();
        String birth = textInputBirthday.getText().toString();
        String email = textInputEmail.getText().toString();
        String password = textInputPassword.getText().toString();
        String phone = textInputPhone.getText().toString();


        Call<Status_handling> call = jsonPlaceHolderApi.createUser(name,birth,email,password,phone);

        call.enqueue(new Callback<Status_handling> () {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,"response "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if(status.getStatus().equals("success")){
                    Toast.makeText(RegisterActivity.this,"Επιτυχής καταχώρηση",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainHomeActivity.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(RegisterActivity.this,"Ανεπιτυχής καταχώρηση\n"+status.getMsg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }





























    private boolean CheckEmail() {
        String emailInput = textInputEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean CheckName() {
        String usernameInput = textInputName.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputName.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputName.setError(null);
            return true;
        }
    }

    private boolean CheckPassword() {
        String passwordInput = textInputPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }


}
