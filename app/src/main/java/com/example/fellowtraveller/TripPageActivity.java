package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TripPageActivity extends AppCompatActivity {

    private Trip trip;
    private TextView textView_status;
    private TextView textView_creator_name;
    private EditText textView_from;
    private EditText textView_to;
    private TextView textView_date;
    private TextView textView_time;
    private TextView textView_seats;
    private TextView textView_suitcases;
    private TextView textView_description;
    private TextView textView_price;
    private Button select,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);
        Intent intent = getIntent();
        trip = intent.getParcelableExtra("Trip");

        textView_status = findViewById(R.id.tripPage_textView_status);
        textView_creator_name = findViewById(R.id.tripPage_textView_creator_name);
        textView_from  = findViewById(R.id.tripPage_textView_from);
        textView_to  = findViewById(R.id.tripPage_textView_to);
        textView_date  = findViewById(R.id.tripPage_textView_date);
        textView_time  = findViewById(R.id.tripPage_textView_time);
        textView_seats  = findViewById(R.id.tripPage_textView_seats);
        textView_suitcases  = findViewById(R.id.tripPage_textView_suitcases);
        textView_description  = findViewById(R.id.tripPage_textView_description);
        textView_price  = findViewById(R.id.tripPage_textView_price);
        select = findViewById(R.id.tripPage_button_sign);
        back = findViewById(R.id.tripPage_button_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        FillFields();


    }





    public void FillFields(){
        String status = trip.getStatus();
        if(status.equals("good")){
            textView_status.setText("Σε εξέλιξη");
        }
        else {
            textView_status.setText("Ολοκληρώθηκε");
            select.setEnabled(false);
        }
        textView_creator_name.setText(trip.getCreator().getName());
        textView_from.setText(trip.getFrom());
        textView_to.setText(trip.getTo());
        textView_date.setText(trip.getDate());
        textView_time.setText(trip.getTime());
        textView_seats.setText(trip.getSeatesStatus());
        textView_suitcases.setText(trip.getSuitcasesStatus());
        textView_description.setText(trip.getDescription());
        textView_price.setText(trip.getPrice()+"€");
    }
}
