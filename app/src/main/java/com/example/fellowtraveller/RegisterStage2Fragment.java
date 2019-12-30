package com.example.fellowtraveller;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage2Fragment extends Fragment {
    private TextInputEditText date,number;
    private DatePickerDialog.OnDateSetListener mDateListener;

    public RegisterStage2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_stage2, container, false);
        date = view.findViewById(R.id.RegisterStage2Fragment__editText_date2);
        number = view.findViewById(R.id.RegisterStage2Fragment__editText_number2);



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


        date.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                date.setCursorVisible(false);
                date.setText(null);
                return true;
            }
        });







        return view;
    }
    public String toString(){
        return "stage2";
    }


    public boolean Check(){
        if(CheckDate() & CheckTime()){
            return true;
        }else{
            return false;
        }
    }

    private boolean CheckDate() {
        String Input = date.getText().toString().trim();

        if (Input.isEmpty()) {
            date.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            date.setError(null);
            return true;
        }
    }

    private boolean CheckTime() {
        String Input = number.getText().toString().trim();

        if (Input.isEmpty()) {
            number.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            number.setError(null);
            return true;
        }
    }

    public String getDate() {
        return date.getText().toString();
    }

    public String getNumber() {
        return number.getText().toString();
    }
}
