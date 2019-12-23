package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hsalf.smilerating.SmileRating;

public class WriteReviewActivity extends AppCompatActivity {
    private ImageButton reliableHappy,reliableNeutral,reliableSad;
    private SmileRating smileRatingCarefull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);



        smileRatingCarefull = (SmileRating)findViewById(R.id.smileRatingCarefull);
        smileRatingCarefull.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {

            }
        });

        reliableHappy = findViewById(R.id.reliable_happy_btn);
        reliableNeutral = findViewById(R.id.reliable_neutral_btn);
        reliableHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
