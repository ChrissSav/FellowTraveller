package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SearchOfferActivity extends AppCompatActivity {
    private Button btn_back,btn_search;
    private DatePickerDialog.OnDateSetListener mDateListener1;
    private DatePickerDialog.OnDateSetListener mDateListener2;
    private TimePickerDialog.OnTimeSetListener mTimeListener1;
    private TimePickerDialog.OnTimeSetListener mTimeListener2;
    private TextInputEditText date_from;
    private TextInputEditText date_to;
    private TextInputEditText time_from;
    private TextInputEditText time_to;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;
    private  Search_item_filter filter  = new Search_item_filter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offer);
        btn_back = findViewById(R.id.search_offer_button_back);
        btn_search = findViewById(R.id.new_offer_button_search);

        String KEY = getString(R.string.api_key);

        autoCompleteTextViewFrom = findViewById(R.id.search_offer_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(SearchOfferActivity.this, android.R.layout.simple_list_item_1, KEY));
        autoCompleteTextViewTo = findViewById(R.id.search_offer_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(SearchOfferActivity.this, android.R.layout.simple_list_item_1, KEY));


        date_from = findViewById(R.id.search_offer_editText_date_from);
        date_to = findViewById(R.id.search_offer_editText_date_to);
        time_from = findViewById(R.id.search_offer_editText_time_from);
        time_to = findViewById(R.id.search_offer_editText_time_to);


        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        SearchOfferActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();

            }
        });


        mDateListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mon, d;
                if (month <= 9) {
                    mon = "0" + month;
                } else {
                    mon = month + "";
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = day + "";
                }
                String date = d + "/" + mon + "/" + year;
                date_from.setText(date + "");
            }
        };


        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        SearchOfferActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
                }

        });


        mDateListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mon, d;
                if (month <= 9) {
                    mon = "0" + month;
                } else {
                    mon = month + "";
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = day + "";
                }
                String date = d + "/" + mon + "/" + year;
                date_to.setText(date + "");
            }
        };


        time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        SearchOfferActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeListener1, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();

                OutFocus();

            }
        });
        mTimeListener1 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour, min;
                if (hourOfDay <= 9) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = hourOfDay + "";
                }
                if (minute <= 9) {
                    min = "0" + minute;
                } else {
                    min = minute + "";
                }
                String time = hour + ":" + min;
                time_from.setText(time + "");
            }
        };

        time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int minute = cal.get(Calendar.MINUTE);
                    TimePickerDialog dialog = new TimePickerDialog(
                            SearchOfferActivity.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,
                            mTimeListener2, hour, minute, true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                    dialog.show();
                OutFocus();
            }
        });
        mTimeListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour, min;
                if (hourOfDay <= 9) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = hourOfDay + "";
                }
                if (minute <= 9) {
                    min = "0" + minute;
                } else {
                    min = minute + "";
                }
                String time = hour + ":" + min;
                time_to.setText(time + "");
            }
        };


        btn_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CheckFieldes()){
                    FilFilter();
                    Intent intent = new Intent(SearchOfferActivity.this, ViewSearchOffersActivity.class);
                    intent.putExtra("Filter",filter);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                onBackPressed();
                finish();

            }
        });

        getWindow().getDecorView().clearFocus();

        time_to.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                time_to.setCursorVisible(false);
                time_to.setText(null);
                return true;
            }
        });

        time_from.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                time_from.setCursorVisible(false);
                time_from.setText(null);
                return true;
            }
        });


        date_from.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                date_from.setCursorVisible(false);
                date_from.setText(null);
                return true;
            }
        });


        date_to.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                date_to.setCursorVisible(false);
                date_to.setText(null);
                return true;
            }
        });



    }

    public void OutFocus(){
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();
    }



    public boolean CheckFieldes(){
        if(CheckTime() & CheckDate() & CheckAutoCompleteTextViewFrom() & CheckAutoCompleteTextViewTo ()){
            return true;
        }
        return false;
    }

    public boolean CheckAutoCompleteTextViewFrom(){
        String from_Input = autoCompleteTextViewFrom.getText().toString().trim();
        if (from_Input.isEmpty()) {
            autoCompleteTextViewFrom.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            autoCompleteTextViewFrom.setError(null);
            return true;
        }
    }
    public boolean CheckAutoCompleteTextViewTo(){
        String to_Input = autoCompleteTextViewTo.getText().toString().trim();
        if (to_Input.isEmpty()) {
            autoCompleteTextViewTo.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            autoCompleteTextViewTo.setError(null);
            return true;
        }
    }

    public boolean CheckDate_to(){
        String date_from_Input = date_from.getText().toString().trim();
        if (date_from_Input.isEmpty()) {
            date_from.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            date_from.setError(null);
            return true;
        }
    }
    public boolean CheckTime(){
        String time_from_Input = time_from.getText().toString().trim();
        String time_to_Input = time_to.getText().toString().trim();
        if(!time_to_Input.isEmpty()){
            if(!time_from_Input.isEmpty()){
                if(CompareTime(time_from_Input,time_to_Input)){
                    Log.i("CheckTime","1");
                    time_from.setError(null);
                    time_to.setError(null);
                    return  true;
                }else {
                    Log.i("CheckTime","2");
                    time_from.setError("");
                    time_to.setError("");
                    return  false;
                }
            }else{
                Log.i("CheckTime","3");
                time_from.setError("");
                time_to.setError("");
                return  false;
            }
        }else{
            Log.i("CheckTime","4");
            time_from.setError(null);
            time_to.setError(null);
            return true;
        }
    }

    public boolean CheckDate(){
        String date_from_Input = date_from.getText().toString().trim();
        String date_to_Input = date_to.getText().toString().trim();
        if(!date_to_Input.isEmpty()){
            if(!date_from_Input.isEmpty()){
                if(CompareDate(date_from_Input,date_to_Input)){
                    Log.i("CheckTime","1");
                    date_from.setError(null);
                    date_to.setError(null);
                    return  true;
                }else {
                    Log.i("CheckTime","2");
                    date_from.setError("");
                    date_to.setError("");
                    return  false;
                }
            }else{
                Log.i("CheckTime","3");
                date_from.setError("");
                date_to.setError("");
                return  false;
            }
        }else{
            Log.i("CheckTime","4");
            date_from.setError(null);
            date_to.setError(null);
            return true;
        }
    }


    public boolean CompareTime(String time1,String time2){
        String str[] = time1.split(":");
        String str2[] = time2.split(":");
        int hour1,hour2,minute1,minute2;
        List<String> d;
        d = Arrays.asList(str);
        hour1 = Integer.parseInt(d.get(0));
        minute1 = Integer.parseInt(d.get(1));
        d = Arrays.asList(str2);
        hour2 = Integer.parseInt(d.get(0));
        minute2 = Integer.parseInt(d.get(1));
        if(hour1<hour2){
            if(minute1<=minute2)
                return true;
        }else if(hour1==hour2){
            if(minute1<minute2)
                return true;
        }
        return false;
    }

    public boolean CompareDate(String date1,String date2){
        String str[] = date1.split("/");
        String str2[] = date2.split("/");

        int day1,day2,month1,month2,year1,year2;
        List<String> d;
        d = Arrays.asList(str);
        day1 = Integer.parseInt(d.get(0));
        month1 = Integer.parseInt(d.get(1));
        year1 = Integer.parseInt(d.get(2));
        d = Arrays.asList(str2);
        day2 = Integer.parseInt(d.get(0));
        month2 = Integer.parseInt(d.get(1));
        year2 = Integer.parseInt(d.get(2));
        if (year1 < year2)
            return true;
        if (year1 > year2)
            return false;
        if (month1 > month2)
            return false;
        if (month1 < month2)
            return true;
        if (day1 < day2)
            return true;
        return false;
    }
    public void FilFilter(){
        filter.setFrom(autoCompleteTextViewFrom.getText().toString());
        filter.setTo(autoCompleteTextViewTo.getText().toString());
        if(!date_from.getText().toString().trim().isEmpty()){
            filter.setDate_from(date_from.getText().toString());
        }
        if(!date_to.getText().toString().trim().isEmpty()){
            filter.setDate_from(date_to.getText().toString());
        }
        if(!time_from.getText().toString().trim().isEmpty()){
            filter.setDate_from(time_from.getText().toString());
        }
        if(!time_to.getText().toString().trim().isEmpty()){
            filter.setDate_from(time_to.getText().toString());
        }

    }
}
