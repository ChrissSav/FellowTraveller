package com.example.fellowtraveller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class ProsforesFragment extends Fragment {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private ArrayList <OfferItem> myExampleList;


    public ProsforesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_prosfores, container, false);

       createExampleList();
       buildRecyclerView(view);

       return view;

    }

    public void createExampleList(){
        myExampleList = new ArrayList<>();
        myExampleList.add(new OfferItem("Αθήνα", "Θεσσαλονίκη", "Σπύρος Ράντογλου","+1"));
        myExampleList.add(new OfferItem("Αθήνα", "Θεσσαλονίκη", "Ιωάννα Δριβάκου","+2"));
        myExampleList.add(new OfferItem("Θεσσαλονίκη", "Καϊμακτσαλάν", "Χρήστος Σαβλίδης","+3"));
        myExampleList.add(new OfferItem("Θεσσαλονίκη", "Λάρισα", "Ρετζίνο Πρίφτι","+4"));
        myExampleList.add(new OfferItem("Φιλώτας", "Θεσσαλονίκη", "Σπύρος Ράντογλου","+5"));
        myExampleList.add(new OfferItem("Αθήνα", "Θεσσαλονίκη", "Σπύρος Ράντογλου","+6"));
        myExampleList.add(new OfferItem("Αθήνα", "Βόλος", "Σπύρος Ράντογλου","+7"));
        myExampleList.add(new OfferItem("Πτολεμαίδα", "Φιλώτας", "Σπύρος Ράντογλου","+8"));
        myExampleList.add(new OfferItem("Καλαμαριά", "Συκιές", "Χρήστος Σαβλίδης","+9"));
        myExampleList.add(new OfferItem("Ναύπακτος", "Λάρισα", "Ρετζίνο Πρίφτι","+10"));

    }

    public void buildRecyclerView(View v){
        myRecyclerView = v.findViewById(R.id.recyclerViewOffer);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myAdapter = new OfferItemAdapter(myExampleList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);


    }

}