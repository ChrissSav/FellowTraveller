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


public class SearchFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  ArrayList<SearchItem> mExampleList ;

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
        mExampleList.add(new SearchItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+1"));
        mExampleList.add(new SearchItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+2"));
        mExampleList.add(new SearchItem("Ξανθη", "Θεσσαλονικη", "Φωτης Πεχλ","+3"));
        mExampleList.add(new SearchItem("Ξανθη", "Θεσσαλονικη", "Φωτης Πεχλ","+4"));
        mExampleList.add(new SearchItem("Ξανθη", "Θεσσαλονικη", "Φωτης Πεχλ","+5"));
        mExampleList.add(new SearchItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+8"));
        mExampleList.add(new SearchItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+9"));
        mExampleList.add(new SearchItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+10"));
        mExampleList.add(new SearchItem("Λαρισα", "Θεσσαλονικη", "Ρετζινο Ποφτ","+11"));


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
                Toast.makeText(getActivity(), position+"",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}