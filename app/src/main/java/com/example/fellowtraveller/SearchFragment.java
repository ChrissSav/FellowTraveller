package com.example.fellowtraveller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

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
    private ArrayList<TripB> ListOfTrips;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit;
    private View mMainView;
    private SwipeRefreshLayout swipeRefreshLayout;
    //=============================
    private ConstraintLayout constraintLayout, constraintLayout_to_hide;
    private ConstraintSet constraintSetOld = new ConstraintSet();
    private ConstraintSet constraintSetNew = new ConstraintSet();
    private TextView textView_count_stand_by;
    private Button view_stand_by;
    private RecyclerView mRecyclerView_stand_by;
    private StandByAdapter mAdapter_stand_by;
    private RecyclerView.LayoutManager mLayoutManager_stand_by;
    private ArrayList<TripB> ListOfStand_by;
    //=============================
    private int cancelPosition = -1;
    private boolean altLayout;
    private GlobalClass globalClass;
    private boolean flag1, flag2;

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_search, container, false);
        flag1 = flag2 = false;
        swipeRefreshLayout = mMainView.findViewById(R.id.SearchFragment_SwipeRefreshLayout);
        view_stand_by = mMainView.findViewById(R.id.SearchFragment_button_stand_by);
        constraintLayout_to_hide = mMainView.findViewById(R.id.constraintLayout2);
        textView_count_stand_by = mMainView.findViewById(R.id.SearchFragment_textView_num_of_StandBy);
        globalClass = (GlobalClass) getActivity().getApplicationContext();

        BuildContainer();
        return mMainView;
    }


    public void BuildContainer() {
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api_url)).addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);

        constraintLayout = mMainView.findViewById(R.id.SearchFragment_Layout_layout);
        constraintSetOld.clone(constraintLayout);
        constraintSetNew.clone(mMainView.getContext(), R.layout.fragment_search_alt);

        ListOfStand_by = new ArrayList<>();
        ListOfTrips = new ArrayList<>();
        if (ListOfStand_by.size() == 0)
            constraintLayout_to_hide.setVisibility(View.GONE);
        getTripsStandBy();
        if (!globalClass.getListOfTrips_to_SearchFragment().isEmpty())
            getTrips();
        else
            ListOfTrips = globalClass.getListOfTrips_to_SearchFragment();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTrips();
                getTripsStandBy();
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        while (!flag1 && !flag2) {

                        }
                        return "null";
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        swipeRefreshLayout.setRefreshing(false);


                    }
                }.execute();
            }
        });
        view_stand_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transition changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(constraintLayout, changeBounds);
                if (!altLayout) {
                    constraintSetNew.applyTo(constraintLayout);
                    altLayout = true;
                } else {
                    constraintSetOld.applyTo(constraintLayout);
                    altLayout = false;
                }

            }
        });
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
                intent.putExtra("Trip", ListOfTrips.get(position));
                intent.putExtra("F", false);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }


    public void buildRecyclerView2() {
        mRecyclerView_stand_by = mMainView.findViewById(R.id.SearchFragment_recyclerView_StandBy);
        mRecyclerView_stand_by.setHasFixedSize(true);
        mLayoutManager_stand_by = new LinearLayoutManager(getActivity());
        mAdapter_stand_by = new StandByAdapter(ListOfStand_by);
        mRecyclerView_stand_by.setLayoutManager(mLayoutManager_stand_by);
        mRecyclerView_stand_by.setAdapter(mAdapter_stand_by);
        mAdapter_stand_by.setOnItemClickListener(new StandByAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (cancelPosition == position) {
                    CancelTrip(position);
                } else {
                    Toast.makeText(getActivity(), "Είσαι σίγουρος ότι θες να ακυρώσεις την αίτηση", Toast.LENGTH_SHORT).show();
                    cancelPosition = position;
                }

            }
        });
    }


    private void getTrips() {
        flag2 = false;
        if (CheckInternetConnection()) {
            Call<List<TripB>> call = jsonPlaceHolderApi.getTripsTakesPart(loadUserId());
            call.enqueue(new Callback<List<TripB>>() {
                @Override
                public void onResponse(Call<List<TripB>> mcall, Response<List<TripB>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), "response " + response.message(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<TripB> trips = response.body();
                    ListOfTrips.clear();
                    for (int i = 0; i < trips.size(); i++) {
                        ListOfTrips.add(trips.get(i));
                    }

                    buildRecyclerView(mMainView);
                    flag2 = true;
                }

                @Override
                public void onFailure(Call<List<TripB>> call, Throwable t) {
                    //Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    flag2 = true;
                }
            });
        } else {
            flag2 = true;
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getTripsStandBy() {
        flag1 = false;
        Call<List<TripB>> call = jsonPlaceHolderApi.getTripsStandBy(loadUserId());
        call.enqueue(new Callback<List<TripB>>() {
            @Override
            public void onResponse(Call<List<TripB>> mcall, Response<List<TripB>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "response " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TripB> trips = response.body();
                ListOfStand_by.clear();
                for (int i = 0; i < trips.size(); i++) {
                    ListOfStand_by.add(trips.get(i));
                }
                if (ListOfStand_by.size() == 0) {
                    constraintLayout_to_hide.setVisibility(View.GONE);
                } else {
                    textView_count_stand_by.setText(ListOfStand_by.size() + "");
                    constraintLayout_to_hide.setVisibility(View.VISIBLE);
                    buildRecyclerView2();
                }

                flag1 = true;


            }

            @Override
            public void onFailure(Call<List<TripB>> call, Throwable t) {
                //Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                flag2 = true;
            }
        });

    }

    private void CancelTrip(final int pos) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("trip_id", ListOfStand_by.get(pos).getId());
        jsonObject.addProperty("trip_creator_id", ListOfStand_by.get(pos).getCreator().getId());
        jsonObject.addProperty("user_id", loadUserId());
        Log.i("CancelTrip", "trip_id : " + ListOfStand_by.get(pos).getId());
        Log.i("CancelTrip", "trip_creator_id : " + ListOfStand_by.get(pos).getCreator().getId());
        Log.i("CancelTrip", "user_id : " + loadUserId());
        Call<Status_handling> call = jsonPlaceHolderApi.CancelTripById(jsonObject);
        call.enqueue(new Callback<Status_handling>() {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "response " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status_handling = response.body();
                if (status_handling.getStatus().equals("success")) {
                    Toast.makeText(getActivity(), "επιτυχης", Toast.LENGTH_SHORT).show();
                    ListOfStand_by.remove(pos);
                    if (ListOfStand_by.size() == 0) {
                        constraintLayout_to_hide.setVisibility(View.GONE);
                    }
                    buildRecyclerView2();
                } else {
                    Toast.makeText(getActivity(), "αποτυχης", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                //Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public int loadUserId() {
        int id = 0;
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(getString(R.string.FILE_USER_INFO));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i == 1) {
                    id = Integer.parseInt(text);
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


    public boolean CheckInternetConnection() {
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