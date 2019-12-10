package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TripPageCreatorActivity extends AppCompatActivity {
    private Trip trip;
    private TextView textView_status;
    private TextView textView_creator_name;
    private EditText textView_from;
    private EditText textView_to;
    private TextView textView_date;
    private TextView textView_time;
    private TextView textView_seats;
    private TextView textView_suitcases;
    private Button back;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page_creator);
        Intent intent = getIntent();
        trip = intent.getParcelableExtra("Trip");

        textView_status = findViewById(R.id.TripPageCreatorActivity_textView_status);
        textView_creator_name = findViewById(R.id.TripPageCreatorActivity_textView_creator_name);
        textView_from  = findViewById(R.id.TripPageCreatorActivity_textView_from);
        textView_to  = findViewById(R.id.TripPageCreatorActivity_textView_to);
        textView_date  = findViewById(R.id.TripPageCreatorActivity_textView_date);
        textView_time  = findViewById(R.id.TripPageCreatorActivity_textView_time);
        textView_seats  = findViewById(R.id.TripPageCreatorActivity_textView_seats);
        textView_suitcases  = findViewById(R.id.TripPageCreatorActivity_textView_suitcases);
        back = findViewById(R.id.TripPageCreatorActivity_button_back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    public void FillFields(){
        String status = trip.getState();
        if(status.equals("available")){
            textView_status.setText("Σε εξέλιξη");
        }
        else {
            textView_status.setText("Ολοκληρώθηκε");
        }
        textView_creator_name.setText(trip.getCreator().getName());
        textView_from.setText(trip.getFfrom());
        textView_to.setText(trip.getTto());
        textView_date.setText(trip.getDate());
        textView_time.setText(trip.getTime());
        textView_seats.setText(trip.getSeatesStatus());
        textView_suitcases.setText(trip.getbagsStatus());
    }
}
