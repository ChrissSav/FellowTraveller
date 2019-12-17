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
    /*public void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.NotificationContainerFragment_RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NotificationAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), position+"",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void p (){
        User user = new User(96,"Makis","22-02-2019","uygygy","uygyy","6934567891",2.0,0,0);
        List<User> pa = new ArrayList<>();
        Trip trip = new Trip("iu","ijr","22-02-2019","22:00",user,pa,"ojoj",10,0,10,0,
                10.0,"va");
        Notification not = new Notification(1,user,trip);
        mExampleList.add(not);
    }

    public void getNotifications() {
        int id = loadUserId();
       // Log.i("NotificationDev","id: "+id);
        Call<List<Notification>> call = jsonPlaceHolderApi.getNotigicationOfUser(id);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> mcall, Response<List<Notification>> response) {
              //  Log.i("NotificationDev","mpika");
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(),"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Notification> trips = response.body();
               // Log.i("NotificationDev","trip size "+trips.size()+"");
                t.setText("Notifications");
                for (int i=0; i<trips.size(); i++){
                    mExampleList.add(trips.get(i));
                    //Log.i("NotificationDev",trips.get(i).getUser().getName());
                }
                //Log.i("NotificationDev","size "+List.size()+"");
            }
            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public int loadUserId() {
        int id =0;
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
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
    }*/
}
