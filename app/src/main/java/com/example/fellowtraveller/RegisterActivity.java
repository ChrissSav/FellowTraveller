package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
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
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;

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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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
        final String name = textInputName.getText().toString();
        String birth = textInputBirthday.getText().toString();
        final String email = textInputEmail.getText().toString();
        String password = textInputPassword.getText().toString();
        String phone = textInputPhone.getText().toString();

        FireBaseRegister(name, email, password, birth, phone);

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
                    save("true",Integer.parseInt(status.getMsg())+"",name,email);
                    //we have to get the id of user and this id save it to the database
                    //Or we have to send the email of user as unique object
                    Intent intent = new Intent(RegisterActivity.this, HomeBetaActivity.class);
                    startActivity(intent);
                    finish();
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






    public void save(String status,String id,String name,String email) {
        //FireBase Database for storing the id of users
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name",name);
        userMap.put("email", email);
        userMap.put("status", status);
        userMap.put("image","default");

        userDatabase.setValue(userMap);
        

        String text = status+"\n"+id+"\n"+name+"\n"+email;
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
    public void FireBaseRegister (String name, String email, String password, String birth, String phone){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //send to main main home activiy
                    //we have to get the id of user and this id save it to the database
                }else{
                    //error
                }
            }
        });

    }

}
