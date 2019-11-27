package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchFiltersActivity extends AppCompatActivity {

    private Button back,apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);

        back = findViewById(R.id.SearchFiltersActivity_button_back);
        apply = findViewById(R.id.SearchFiltersActivity_button_apply);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);

            }
        });
    }
}
