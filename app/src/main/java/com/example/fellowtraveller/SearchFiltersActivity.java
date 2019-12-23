package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SearchFiltersActivity extends AppCompatActivity {

    private Button back,apply;
    private Search_item_filter filter = new Search_item_filter();
    private TextInputEditText seats_min;
    private TextInputEditText seats_max;
    private TextInputEditText bags_min;
    private TextInputEditText bags_max;
    private TextInputEditText rate_min;
    private TextInputEditText rate_max;
    private TextInputEditText price_min;
    private TextInputEditText price_max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);
        Intent intent = getIntent();
        filter =  intent.getParcelableExtra("Filter");
        seats_min = findViewById(R.id.SearchFiltersActivity_editText_from_seats);
        seats_max = findViewById(R.id.SearchFiltersActivity_editText_to_seats);
        bags_min = findViewById(R.id.SearchFiltersActivity_editText_from_suitcases);
        bags_max = findViewById(R.id.SearchFiltersActivity_editText_to_suitcases);
        rate_min = findViewById(R.id.SearchFiltersActivity_editText_from_rate);
        rate_max = findViewById(R.id.SearchFiltersActivity_editText_to_suitcases);
        price_min = findViewById(R.id.SearchFiltersActivity_editText_from_price);
        price_max = findViewById(R.id.SearchFiltersActivity_editText_to_price);
        back = findViewById(R.id.SearchFiltersActivity_button_back);
        apply = findViewById(R.id.SearchFiltersActivity_button_apply);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("filter", filter);
                setResult(RESULT_OK, resultIntent);
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refreshfields();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("filter", filter);
                resultIntent.putExtra("flag", true);
                setResult(RESULT_OK, resultIntent);
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);

            }
        });
        Fillfields();
    }

    public void Fillfields(){
        if(filter.getBags_from()!=0)
            bags_min.setText(filter.getBags_from()+"");
        if(filter.getBags_to()!=0)
            bags_max.setText(filter.getBags_to()+"");
        if(filter.getSeats_from()!=0)
            seats_min.setText(filter.getSeats_from()+"");
        if(filter.getSeats_to()!=0)
            seats_max.setText(filter.getSeats_to()+"");
        if(filter.getRate_from()!=0)
            rate_min.setText(filter.getRate_from()+"");
        if(filter.getRate_to()!=0)
            rate_max.setText(filter.getRate_to()+"");
        if(filter.getPrice_from()!=0)
            price_min.setText(filter.getPrice_from()+"");
        if(filter.getPrice_to()!=0)
            price_max.setText(filter.getPrice_to()+"");

    }


    public void Refreshfields(){
        if(!bags_min.getText().toString().isEmpty()){
            filter.setBags_from(Integer.parseInt(bags_min.getText().toString()));
        }else{
            filter.setBags_from(0);
        }
        if(!bags_max.getText().toString().isEmpty()){
            filter.setBags_to(Integer.parseInt(bags_min.getText().toString()));
        }else{
            filter.setBags_to(0);
        }

        if(!seats_min.getText().toString().isEmpty()){
            filter.setSeats_from(Integer.parseInt(seats_min.getText().toString()));
        }else{
            filter.setSeats_from(0);
        }
        if(!seats_max.getText().toString().isEmpty()){
            filter.setSeats_to(Integer.parseInt(seats_max.getText().toString()));
        }else{
            filter.setSeats_to(0);
        }
        if(!rate_min.getText().toString().isEmpty()){
            filter.setRate_from(Double.parseDouble(rate_min.getText().toString()));
        }else{
            filter.setRate_from(0);
        }
        if(!rate_max.getText().toString().isEmpty()){
            filter.setRate_to(Double.parseDouble(rate_max.getText().toString()));
        }else{
            filter.setRate_to(0);
        }
        if(!price_min.getText().toString().isEmpty()){
            filter.setPrice_from(Integer.parseInt(price_min.getText().toString()));
        }else{
            filter.setPrice_from(0);
        }
        if(!price_max.getText().toString().isEmpty()){
            filter.setPrice_to(Integer.parseInt(price_max.getText().toString()));
        }else{
            filter.setPrice_to(0);
        }



    }
}
