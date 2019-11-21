package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchOfferActivity extends AppCompatActivity {
    private Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offer);
        btn_back = findViewById(R.id.search_offer_button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                    SearchOfferActivity.this.onBackPressed();

            }
        });
    }
}
