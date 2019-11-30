package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewOfferActivity extends AppCompatActivity {
    private Button btn_back,register;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;
    private TextInputEditText date_trip;
    private TextInputEditText time_trip;
    private TextInputEditText description;
    private TextInputEditText seats;
    private TextInputEditText bags;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        String KEY = getString(R.string.api_key);

        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        description = findViewById(R.id.new_offer_editText_description);
        seats = findViewById(R.id.new_offer_editText_seats);
        bags = findViewById(R.id.new_offer_editText_cases);
        autoCompleteTextViewFrom = findViewById(R.id.new_offer_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(NewOfferActivity.this,android.R.layout.simple_list_item_1,KEY));
        autoCompleteTextViewTo = findViewById(R.id.new_offer_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(NewOfferActivity.this,android.R.layout.simple_list_item_1,KEY));

        register = findViewById(R.id.new_offer_button_register);
        btn_back = findViewById(R.id.new_offer_button_back);
        date_trip = findViewById(R.id.new_offer_editText_date);
        time_trip = findViewById(R.id.new_offer_editText_time);


        date_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        NewOfferActivity.this,
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
                date_trip.setText(date+"");
            }
        };



        time_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        NewOfferActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeListener, hour, minute,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour,min;
                if (hourOfDay<=9){
                    hour = "0"+hourOfDay;
                }
                else{
                    hour = hourOfDay+"";
                }
                if(minute<=9){
                    min = "0"+minute;
                }
                else{
                    min = minute+"";
                }
                String time = hour + ":" + min;
                time_trip.setText(time+"");
            }
        };


        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                NewOfferActivity.this.onBackPressed();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                try {
                    registerTrip();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void OutFocus(){
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();
        description.clearFocus();
        seats.clearFocus();
        bags.clearFocus();
    }



    public void registerTrip() throws InterruptedException {

        String from = autoCompleteTextViewFrom.getText().toString();
        String to = autoCompleteTextViewTo.getText().toString();
        String date = date_trip.getText().toString();
        date = date.replaceAll("/","-");
        String time = time_trip.getText().toString();
        String des = description.getText().toString();
        int max_seats = Integer.parseInt(seats.getText().toString());
        int  max_bags = Integer.parseInt(bags.getText().toString());
        
        Call<List<Status_handling>> call = jsonPlaceHolderApi.createTrip(from,to,date,time,1,des,max_seats,max_bags);
        call.toString();
        call.enqueue(new Callback<List<Status_handling>>() {
            @Override
            public void onResponse(Call<List<Status_handling>> mcall, Response<List<Status_handling>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(NewOfferActivity.this,"responseb "+response.errorBody()+"\n"+"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Status_handling> status = response.body();
                if(status.get(0).getStatus().equals("success")){
                    Toast.makeText(NewOfferActivity.this,"Επιτυχής καταχώρηση",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(NewOfferActivity.this, MainHomeActivity.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(NewOfferActivity.this,"Ανεπιτυχής καταχώρηση",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Status_handling>> call, Throwable t) {
                Toast.makeText(NewOfferActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
