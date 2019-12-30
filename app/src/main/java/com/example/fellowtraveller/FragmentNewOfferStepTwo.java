package com.example.fellowtraveller;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNewOfferStepTwo extends Fragment {
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;
    private View view;
    private TextInputEditText date, time,dec;

    public FragmentNewOfferStepTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FragmentTransaction", "FragmentNewOfferStepTwo  onCreateView");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_step_two, container, false);

        date = view.findViewById(R.id.fragment_new_offer_step_two_TextInputEditText_date);
        time = view.findViewById(R.id.fragment_new_offer_step_two_TextInputEditText_time);
        dec =  view.findViewById(R.id.fragment_new_offer_step_two_editText_description);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
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
                String ddate = d + "-" + mon + "-" + year;
                date.setText(ddate + "");
            }
        };


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeListener, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();


            }
        });
        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
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
                String tiime = hour + ":" + min;
                time.setText(tiime + "");
            }
        };

        date.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                date.setCursorVisible(false);
                date.setText(null);
                return true;
            }
        });

        time.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                time.setCursorVisible(false);
                time.setText(null);
                return true;
            }
        });

        return view;
    }

    public String toString() {
        return "stage2";
    }


    public void onStart() {
        super.onStart();
        Log.i("FragmentTransaction", "FragmentNewOfferStepTwo  onStart");

    }


    public boolean Check() {
        if (CheckDate() & Checktime())
            return true;
        else
            return false;
    }


    private boolean CheckDate() {
        String d = date.getText().toString().trim();

        if (d.isEmpty()) {
            date.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            date.setError(null);
            return true;
        }
    }

    public String getDec() {
        return dec.getText().toString();
    }

    private boolean Checktime() {
        String t = time.getText().toString().trim();

        if (t.isEmpty()) {
            time.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            time.setError(null);
            return true;

        }
    }

    public String GetDate(){
        return date.getText().toString();
    }

    public String GetTime(){
        return time.getText().toString();
    }


}
