package com.example.fellowtraveller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    private Button btn1,btn2;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        btn1 = v.findViewById(R.id.frag_home_button_offer);
        btn2 = v.findViewById(R.id.frag_home_button_search);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),NewOfferActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),SearchOfferActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}