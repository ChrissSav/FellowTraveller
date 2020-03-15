package com.example.fellowtraveller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProsforesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OfferFragAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TripB> ListOfTrips ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private View mMainView;
    private int id;
    private ImageView noResultsImage;
    private SwipeRefreshLayout swipeRefreshLayout;


    public ProsforesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_prosfores, container, false);


        loadUserId();

        ListOfTrips = new ArrayList<>();
        swipeRefreshLayout = mMainView.findViewById(R.id.ProsforesFragment_SwipeRefreshLayout);
        noResultsImage = mMainView.findViewById(R.id.no_results);
        getUserTrips();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getUserTrips();

            }
        });
        Log.i("mMainView","onStart");
        return mMainView;
    }

    @Override
    public void onStart(){
        super.onStart();
        /*loadUserId();

        ListOfTrips = new ArrayList<>();
        swipeRefreshLayout = mMainView.findViewById(R.id.ProsforesFragment_SwipeRefreshLayout);
        noResultsImage = mMainView.findViewById(R.id.no_results);
        getUserTrips();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getUserTrips();

            }
        });
        Log.i("mMainView","onStart");*/
    }


    public  void loadUserId() {
        id = 0;
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

    }

    public void buildRecyclerView() {
        mRecyclerView = mMainView.findViewById(R.id.recyclerViewOffer);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new OfferFragAdapter(ListOfTrips);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OfferFragAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), TripPageCreatorActivity.class);
                intent.putExtra("Trip",ListOfTrips.get(position));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });


    }
    private void getUserTrips() {

        if(CheckInternetConnection()){
            retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api_url)).addConverterFactory(GsonConverterFactory.create()).build();
            jsonPlaceHolderApi = retrofit.create(JsonApi.class);
            Call<List<TripB>> call = jsonPlaceHolderApi.getTripsCreated(id);
            call.enqueue(new Callback<List<TripB>>() {
                @Override
                public void onResponse(Call<List<TripB>> mcall, Response<List<TripB>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(),"response "+response.message(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ListOfTrips.clear();
                    List<TripB> trips = response.body();
                    if(trips.size()!=0) {
                        noResultsImage.setVisibility(View.GONE);
                        for (int i = 0; i < trips.size(); i++) {
                            ListOfTrips.add(trips.get(i));
                        }
                        buildRecyclerView();
                        swipeRefreshLayout.setRefreshing(false);
                    }

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