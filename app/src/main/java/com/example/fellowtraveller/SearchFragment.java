package com.example.fellowtraveller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private ArrayList<TripB> ListOfTrips ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private View mMainView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_search, container, false);
        swipeRefreshLayout = mMainView.findViewById(R.id.SearchFragment_SwipeRefreshLayout);
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        ListOfTrips = new ArrayList<>();
        getTrips();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getTrips();

            }
        });
        return mMainView;
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.SearchFragment_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new SearchAdapter(ListOfTrips);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), TripPageActivity.class);
                intent.putExtra("Trip",ListOfTrips.get(position));
                intent.putExtra("F",false);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }



    private void getTrips() {

        if(CheckInternetConnection()){
            Call<List<TripB>> call = jsonPlaceHolderApi.getTripsTakesPart(loadUserId());
            call.enqueue(new Callback<List<TripB>>() {
                @Override
                public void onResponse(Call<List<TripB>> mcall, Response<List<TripB>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(),"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<TripB> trips = response.body();
                    ListOfTrips.clear();
                    for (int i=0; i<trips.size(); i++){
                        ListOfTrips.add(trips.get(i));
                    }

                    buildRecyclerView(mMainView);
                    swipeRefreshLayout.setRefreshing(false);

                }
                @Override
                public void onFailure(Call<List<TripB>> call, Throwable t) {
                    //Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_SHORT).show();
        }
    }


    public int loadUserId() {
        int id =0;
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(getString(R.string.FILE_USER_INFO));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            int i = 0;
            while ((text = br.readLine()) != null) {
                if(i==1){
                    id =  Integer.parseInt(text);
                    break;
                }
                i++;
            }
            return id;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }


    public boolean CheckInternetConnection(){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



}