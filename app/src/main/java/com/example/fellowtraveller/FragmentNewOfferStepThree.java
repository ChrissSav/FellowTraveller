package com.example.fellowtraveller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class FragmentNewOfferStepThree extends Fragment {

    private Button increaseSeat,dincreaseSeat;
    private TextView seats,bags;
    private Button increaseBags,dincreaseBags;
    private TextView totalCost;
    private EditText price;
    private View view;

    public FragmentNewOfferStepThree() {
        Log.i("FragmentTransaction","FragmentNewOfferStepThree");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("FragmentTransaction","FragmentNewOfferStepThree  onCreateView");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_step_three, container, false);
        increaseSeat = view.findViewById(R.id.fragment_new_offer_step_three_seat_plus);
        dincreaseSeat = view.findViewById(R.id.fragment_new_offer_step_three_seat_minus);
        increaseBags = view.findViewById(R.id.fragment_new_offer_step_three_baggage_minus);
        dincreaseBags = view.findViewById(R.id.fragment_new_offer_step_three_baggage_plus);
        seats = view.findViewById(R.id.fragment_new_offer_step_three_seats);
        bags = view.findViewById(R.id.fragment_new_offer_step_three_baggage);
        totalCost= view.findViewById(R.id.fragment_new_offer_step_three_editText_total_price);
        price= view.findViewById(R.id.fragment_fragment_new_offer_step_three_step_three_editText_price);



        return view;
    }



    public void onStart() {
        Log.i("FragmentTransaction","FragmentNewOfferStepThree  onStart");

        super.onStart();
        increaseSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentnum = Integer.parseInt(seats.getText().toString());
                seats.setText(currentnum + 1 + "");
                CalculateCost();

            }
        });

        dincreaseSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentnum = Integer.parseInt(seats.getText().toString());
                if (currentnum == 0) {
                    seats.setText(0 + "");
                } else if (currentnum == 1) {
                    // seats.setText(currentnum-1+"");
                } else {
                    seats.setText(currentnum - 1 + "");
                }
                CalculateCost();
            }
        });


        dincreaseBags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentnum = Integer.parseInt(bags.getText().toString());
                bags.setText(currentnum + 1 + "");
            }
        });

        increaseBags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentnum = Integer.parseInt(bags.getText().toString());
                if (currentnum == 0) {
                    bags.setText(0 + "");
                } else {
                    bags.setText(currentnum - 1 + "");
                }
            }
        });



        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CalculateCost();
            }
        });

    }

    public void CalculateCost(){


        if(price.getText().length()>0) {
            int numofseats =  Integer.parseInt(seats.getText().toString());
            int total = numofseats * Integer.parseInt(price.getText().toString());
            totalCost.setText(total + "");
        }
    }
    public String toString(){
        return "stage3";
    }
    public boolean Check() {

        Log.i("uigurr",price.getText().toString());
        if(price.getText().length()>0) {
            int p = Integer.parseInt(price.getText().toString());
            if (p <= 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;

    }


    public String getBags() {
        return bags.getText().toString();
    }


    public String getSeats() {
        return seats.getText().toString();
    }

    public String getPrice() {
        return price.getText().toString();
    }
}
