package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewOfferActivity extends AppCompatActivity {
    private Button btn_back;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
    private JsonApi jsonPlaceHolderApi= retrofit.create(JsonApi.class);
    private Button btn_next_stage;
    private Fragment fra;
    private TextView tvStage1,tvStage2,tvStage3;
    private FragmentNewOfferStepOne stage1 = new FragmentNewOfferStepOne() ;
    private FragmentNewOfferStepTwo stage2 = new FragmentNewOfferStepTwo();
    private FragmentNewOfferStepThree stage3 = new FragmentNewOfferStepThree() ;
    private FragmentManager fragmentManager;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        globalClass = (GlobalClass) getApplicationContext();

        fragmentManager = getSupportFragmentManager();
        btn_next_stage = findViewById(R.id.new_offer_button_next_fragment);
        btn_back = findViewById(R.id.new_offer_button_back);
        tvStage1 = findViewById(R.id.new_offer_textView_stage1);
        tvStage2 = findViewById(R.id.new_offer_textView_stage2);
        tvStage3 = findViewById(R.id.new_offer_textView_stage3);



        fra = stage1;
        fragmentManager.beginTransaction().replace(R.id.new_offer_container,fra).commit();
        btn_next_stage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(fra.toString().equals("stage1") && stage1.Check()){
                    Log.i("IsStatment","satage1"+stage1.Check());
                    fra = stage2;
                    tvStage2.setBackgroundResource(R.color.orange);
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.new_offer_container,fra).commit();
                }
                else if(fra.toString().equals("stage2") && stage2.Check()){
                    fra = stage3;
                    tvStage3.setBackgroundResource(R.color.orange);
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.new_offer_container,fra).commit();
                    btn_next_stage.setText("Καταχώρηση");
                }else if(fra.toString().equals("stage3") && stage3.Check()){
                    registerTrip();
                    Log.i("IsStatment","registerTrip");
                   // from,String to,String date,String time,int creator_id,String des,int max_seats,int max_bags, int price_trip
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

    }

    public void sendToAPi(String from,String to,String date,String time,int creator_id,String des,int max_seats,int max_bags, int price_trip){
        Call<Status_handling> call = jsonPlaceHolderApi.createTrip(from, to, date, time, creator_id, des, max_seats, max_bags,price_trip);
        call.toString();

        call.enqueue(new Callback<Status_handling>() {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(NewOfferActivity.this, "response " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if (status.getStatus().equals("success")) {
                    Toast.makeText(NewOfferActivity.this, "Επιτυχής καταχώρηση", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewOfferActivity.this, HomeBetaActivity.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(NewOfferActivity.this, "Ανεπιτυχής καταχώρηση", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                Toast.makeText(NewOfferActivity.this, "t: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void registerTrip(){
       // Toast.makeText(NewOfferActivity.this, "rgregerg καταχώρηση", Toast.LENGTH_SHORT).show();

        String from =stage1.GetFrom();
        String to = stage1.GetTo();
        String date = stage2.GetDate();
        date = date.replaceAll("/","-");
        String time = stage2.GetTime();
        String des =stage2.getDec()+ " ";
        int price_trip = Integer.parseInt(stage3.getPrice());
        int max_seats = Integer.parseInt(stage3.getSeats());
        int  max_bags = Integer.parseInt(stage3.getBags());
        int creator_id = globalClass.getId();
        if (creator_id!=0) {
            sendToAPi(from, to, date, time, creator_id, des, max_seats, max_bags,price_trip);
        }
    }

    public void onBackPressed(){
        if(fra.toString().equals("stage1")){
            Intent intent = new Intent(NewOfferActivity.this,HomeBetaActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
        else if(fra.toString().equals("stage2") ){
            fra = stage1;
            tvStage2.setBackgroundResource(R.color.lightOrange);
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right).replace(R.id.new_offer_container,fra).commit();
        }
        else if(fra.toString().equals("stage3")){
            fra = stage2;
            btn_next_stage.setText("Επόμενο");

            tvStage3.setBackgroundResource(R.color.lightOrange);
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right).replace(R.id.new_offer_container,fra).commit();
        }
    }
}



