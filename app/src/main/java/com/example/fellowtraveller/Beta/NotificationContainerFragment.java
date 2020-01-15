package com.example.fellowtraveller.Beta;


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

import com.example.fellowtraveller.JsonApi;
import com.example.fellowtraveller.Notification;
import com.example.fellowtraveller.NotificationAdapter;
import com.example.fellowtraveller.R;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationContainerFragment extends Fragment {
    private TextView t;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Notification> mExampleList;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private final String FILE_NAME = "fellow_login_state.txt";


    public NotificationContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification_container, container, false);
        /*t = view.findViewById(R.id.NotificationContainerFragment_textView);
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        mExampleList = new ArrayList<>();
        p();
        buildRecyclerView(view);*/
        return view;

    }
}
