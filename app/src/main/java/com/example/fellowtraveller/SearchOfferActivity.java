package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchOfferActivity extends AppCompatActivity {
    private Button btn_next_stage,btn_back;
    private Fragment fra;
    private SearchOfferStage1Fragment stage1 = new SearchOfferStage1Fragment() ;
    private SearchOfferStage2Fragment stage2 = new SearchOfferStage2Fragment();
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offer);
        fragmentManager = getSupportFragmentManager();
        btn_next_stage = findViewById(R.id.search_offer_button_next_fragment);
        btn_back = findViewById(R.id.search_offer_button_back);
        fra = stage1;
        fragmentManager.beginTransaction().replace(R.id.new_offer_container,fra).commit();
        btn_next_stage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(fra.toString().equals("stage1")){
                    fra = stage2;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.new_offer_container,fra).commit();
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(fra.toString().equals("stage1")){
                    SearchOfferActivity.this.onBackPressed();
                }
                else if(fra.toString().equals("stage2")) {
                    fra = stage1;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.exit_to_right, R.anim.enter_from_left).replace(R.id.new_offer_container, fra).commit();
                }
            }
        });
    }
}
