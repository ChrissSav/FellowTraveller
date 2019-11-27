package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private Button btn1;
    private TextView text;
    private TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.main_button1);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(mainIntent);
            }
        });

        btn1 = findViewById(R.id.main_button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(mainIntent);
            }
        });
        text = findViewById(R.id.main_textView10);
        text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, MainHomeActivity.class);
                startActivity(mainIntent);
            }
        });

        text1 = findViewById(R.id.main_textView11);
        text1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User creator = new User("Σπυρίδων Ράντογλου", "email","password");
                Intent intent = new Intent(MainActivity.this, TripPageActivity.class);
                String description ="Είστε όλοι άπλυτη. Δε θα μπει κανένας στο αμάξι μου. ";
                Trip trip = new Trip(creator,"Αισώπου 30, Θεσσαλόνίκη","Εγνατιας 30 Αθήνα","23/12/2019","12:00",3,2,description,"100");
                intent.putExtra("Trip",trip);
                startActivity(intent);
            }
        });

    }
}
