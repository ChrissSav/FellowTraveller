package com.example.fellowtraveller;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  ArrayList<Trip> mExampleList ;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        createExampleList();
        buildRecyclerView(view);
        return view;
    }

    public void createExampleList() {
        mExampleList = new ArrayList<>();
        User creator = new User("Σπυρίδων Ράντογλου", "email","password");
        User creator2 = new User("Ρέτζινο Πρίφτη", "email","password");
        User creator3 = new User("Φώτης Πεχλιβάνης", "email","password");
        String description ="Είστε όλοι άπλυτη. Δε θα μπει κανένας στο αμάξι μου. ";
        Trip trip = new Trip(creator,"Αισώπου 30, Θεσσαλόνίκη","Εγνατιας 30 Αθήνα","23/12/2019","12:00",3,2,description,"100");
        mExampleList.add(new Trip(creator,"Αισώπου 30, Θεσσαλόνίκη","Εγνατιας 30 Αθήνα","23/12/2019","12:00",3,2,description,"100"));
        mExampleList.add(new Trip(creator2,"Αισώπου 30, Λάρισα","Εγνατιας 30 Θεσσαλόνίκη","03/12/2019","12:00",3,2,description,"100"));
        mExampleList.add(new Trip(creator2,"Αισώπου 30, Λάρισα","Εγνατιας 30 Θεσσαλόνίκη","13/12/2019","12:00",3,2,description,"100"));
        mExampleList.add(new Trip(creator3,"Ενγατία 120, Θεσσαλόνίκη","Εγνατιας 30 Θεσσαλόνίκη","23/09/2019","02:00",3,2,description,"100"));
        mExampleList.add(new Trip(creator3,"Αισώπου 30, Θεσσαλόνίκη","Εγνατιας 30 Θεσσαλόνίκη","23/11/2019","11:00",3,2,description,"100"));
       /* mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+2"));
        mExampleList.add(new SearchesItem("Ξανθη", "Θεσσαλονικη", "Φωτης Πεχλ","+3"));
        mExampleList.add(new SearchesItem("Ξανθη", "Θεσσαλονικη", "Φωτης Πεχλ","+4"));
        mExampleList.add(new SearchesItem("Ξανθη", "Θεσσαλονικη", "Φωτης Πεχλ","+5"));
        mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+6"));
        mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+7"));
        mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+8"));
        mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+9"));
        mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+10"));
        mExampleList.add(new SearchesItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+11"));*/


    }
    public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new SearchAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), TripPageActivity.class);
                intent.putExtra("Trip",mExampleList.get(position));
                startActivity(intent);
            }
        });
    }
}