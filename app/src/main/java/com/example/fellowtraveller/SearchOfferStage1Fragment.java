package com.example.fellowtraveller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class SearchOfferStage1Fragment extends Fragment {


    public SearchOfferStage1Fragment() {
        // Required SearchOfferActivityStage1Fragment public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_offer_stage1, container, false);
    }

}
