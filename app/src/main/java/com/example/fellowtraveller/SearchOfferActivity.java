package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

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
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        SearchOfferActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeListener1, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
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
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        SearchOfferActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeListener2, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
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
                Search_item_filter filter  = new Search_item_filter(autoCompleteTextViewFrom.getText().toString(),autoCompleteTextViewTo.getText().toString(),
                        date_from.getText().toString(),date_to.getText().toString(),time_from.getText().toString(),time_to.getText().toString());
                SearchOfferActivity.this.onBackPressed();
                Intent intent = new Intent(SearchOfferActivity.this, ViewSearchOffersActivity.class);
                intent.putExtra("Filter",filter);
                startActivity(intent);
                finish();

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

    }

    public void OutFocus(){
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();
    }
}
