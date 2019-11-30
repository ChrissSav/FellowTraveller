package com.example.fellowtraveller;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Trip> Listoftrips ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private TextView textError;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        textError = view.findViewById(R.id.SearchFragment_textView3);
        Listoftrips = new ArrayList<>();
        createUser();
        buildRecyclerView(view);
        return view;
    }


    public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new SearchAdapter(Listoftrips);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), TripPageActivity.class);
                intent.putExtra("Trip",Listoftrips.get(position));
                startActivity(intent);
                getActivity().finish();
            }
        });
    }



    private void createUser() {

        Call<List<Trip>> call = jsonPlaceHolderApi.getTrips();
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> mcall, Response<List<Trip>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(),"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                textError.setText("");

                List<Trip> trips = response.body();
                for (int i=0; i<trips.size(); i++){
                    Log.i("RestAPI","i: "+i);

                    Listoftrips.add(trips.get(i));
                }
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                textError.setText("t: "+t.getMessage());
                Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}