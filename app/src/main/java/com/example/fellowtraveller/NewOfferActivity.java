package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class NewOfferActivity extends AppCompatActivity {
    private Button btn_back;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;
    private TextInputEditText date_trip;
    private TextInputEditText time_trip;
    private TextInputEditText description;
    private TextInputEditText seats;
    private TextInputEditText sutcases;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        String KEY = getString(R.string.api_key);


        description = findViewById(R.id.new_offer_editText_description);
        seats = findViewById(R.id.new_offer_editText_seats);
        sutcases = findViewById(R.id.new_offer_editText_cases);
        autoCompleteTextViewFrom = findViewById(R.id.new_offer_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(NewOfferActivity.this,android.R.layout.simple_list_item_1,KEY));
        autoCompleteTextViewTo = findViewById(R.id.new_offer_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(NewOfferActivity.this,android.R.layout.simple_list_item_1,KEY));


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
    }

    public void OutFocus(){
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();
        description.clearFocus();
        seats.clearFocus();
        sutcases.clearFocus();
    }

}
