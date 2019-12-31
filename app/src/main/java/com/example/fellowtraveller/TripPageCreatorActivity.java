package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripPageCreatorActivity extends AppCompatActivity {
    private TripB trip;
    private TextView textView_status;
    private TextView textView_creator_name;
    private EditText textView_from;
    private EditText textView_to;
    private TextView textView_date;
    private TextView textView_time;
    private TextView textView_seats;
    private TextView textView_bags;
    private Button back;
    private TextView textView_rate;
    private List<UserB> requests ;
    private List<UserB> passengers ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private CircleImageView img;
    private int id =0 ;
    private final String FILE_NAME = "fellow_login_state.txt";
    private final String ACCEPT = "accept";
    private final String REJECT = "reject";
    private TextView textView_price;
    private RecyclerView mRecyclerView;
    private RequestAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerViewPassengers;
    private PassengerAdapter mAdapterPassengers;
    private RecyclerView.LayoutManager mLayoutManagerP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page_creator);
        Intent intent = getIntent();
        trip = intent.getParcelableExtra("Trip");
        requests = trip.getRequests();
        passengers = new ArrayList<>();
        passengers = trip.getPassengers();
        textView_rate = findViewById(R.id.TripPageCreatorActivity_textView_rate);
        img = findViewById(R.id.TripPageCreatorActivity_textView_adminImage);
        textView_status = findViewById(R.id.TripPageCreatorActivity_textView_status);
        textView_creator_name = findViewById(R.id.TripPageCreatorActivity_textView_creator_name);
        textView_from  = findViewById(R.id.TripPageCreatorActivity_textView_from);
        textView_to  = findViewById(R.id.TripPageCreatorActivity_textView_to);
        textView_date  = findViewById(R.id.TripPageCreatorActivity_textView_date);
        textView_time  = findViewById(R.id.TripPageCreatorActivity_textView_time);
        textView_seats  = findViewById(R.id.TripPageCreatorActivity_textView_seats);
        textView_bags  = findViewById(R.id.TripPageCreatorActivity_textView_suitcases);
        textView_price  = findViewById(R.id.TripPageCreatorActivity_textView_price);
        back = findViewById(R.id.TripPageCreatorActivity_button_back);
        FillFields();
        buildRecyclerViewPassengers();
        buildRecyclerView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        textView_rate.setText(trip.getCreator().getRate()+"");
        if(!trip.getCreator().getPicture().equals("null")){
            img.setImageBitmap(StringToBitMap(trip.getCreator().getPicture()));
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.TripPageCreatorActivity_RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(TripPageCreatorActivity.this);
        mAdapter = new RequestAdapter(requests);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,int flag) {
                //flag==0 accept
                if(flag == 0){
                    if(CheckΑvailability(position)) {
                        getUserTrips(requests.get(position).getId(), trip.getId(), ACCEPT, position);
                    }
                }//flag==1 reject
                else if(flag == 1){
                    getUserTrips(requests.get(position).getId(),trip.getId(),REJECT,position);
                }
            }
        });
    }

    public void Addpassenger(int position){
        passengers.add(requests.get(position));
        mAdapterPassengers.notifyDataSetChanged();
    }


    public void Increase(int position) {
        trip.setCurrent_num_of_seats(trip.getCurrent_num_of_seats() + 1);
        textView_seats.setText(trip.getSeatesStatus());
        if (requests.get(position).getBag().equals("yes")){
            trip.setCurrent_num_of_bags(trip.getCurrent_num_of_bags() + 1);
            textView_bags.setText(trip.getbagsStatus());

        }

    }

    public boolean  CheckΑvailability(int position){
        if(trip.getCurrent_num_of_seats()+1 <= trip.getMax_seats()){
            if(requests.get(position).getBag().equals("yes")){
                if(trip.getCurrent_num_of_bags()+1 <= trip.getMax_bags()){
                    return  true;
                }
                else{
                    Toast.makeText(TripPageCreatorActivity.this,"Δεν υπάρχει διαθεσημότητα για αποσκεύη! ",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                return true;
            }
        }else {
            Toast.makeText(TripPageCreatorActivity.this,"Η διαδρομή είναι πλήρης ",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void buildRecyclerViewPassengers() {
            mRecyclerViewPassengers = findViewById(R.id.TripPageCreatorActivity_RecyclerView_passengers);
            mRecyclerViewPassengers.setHasFixedSize(true);
            mLayoutManagerP = new LinearLayoutManager(TripPageCreatorActivity.this);
            mAdapterPassengers = new PassengerAdapter(passengers);
            mRecyclerViewPassengers.setLayoutManager(mLayoutManagerP);
            mRecyclerViewPassengers.setAdapter(mAdapterPassengers);

    }

    public void Delete(int pos){
        requests.remove(pos);
        mAdapter.notifyDataSetChanged();
    }
    public void FillFields(){
        String status = trip.getState();
        if(status.equals("available")){
            textView_status.setText("Σε εξέλιξη");
        }
        else {
            textView_status.setText("Ολοκληρώθηκε");
        }
        textView_creator_name.setText(trip.getCreator().getName());
        textView_from.setText(trip.getFfrom());
        textView_to.setText(trip.getTto());
        textView_date.setText(trip.getDate());
        textView_time.setText(trip.getTime());
        textView_seats.setText(trip.getSeatesStatus());
        textView_bags.setText(trip.getbagsStatus());
        textView_price.setText("Τιμή : "+trip.getPrice()+" ευρώ");
    }



    public void loadUserInfo() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            int i = 0;
            while ((text = br.readLine()) != null) {
                if(i==1){
                    id = Integer.parseInt(text);
                }
                i++;
                break;
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


    private void getUserTrips(int user_id, int trip_id, final String status, final int position) {
        final String st = status;
        if(CheckInternetConnection()){
            retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
            jsonPlaceHolderApi = retrofit.create(JsonApi.class);
            final Call<Status_handling> call = jsonPlaceHolderApi.ChangeRequestStatus(user_id,requests.get(position).getBag(), trip_id,status);
            call.enqueue(new Callback<Status_handling>() {
                @Override
                public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(TripPageCreatorActivity.this,"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Status_handling status_han = response.body();
                    if(status_han.getStatus().equals("success")){
                        if(st.equals(ACCEPT)){
                            Toast.makeText(TripPageCreatorActivity.this,"Εγκρίθηκε το αίτημα του χρήστη "+requests.get(position).getName(),Toast.LENGTH_SHORT).show();
                            Addpassenger(position);
                            Increase(position);
                            Delete(position);

                        }else{
                            Toast.makeText(TripPageCreatorActivity.this,"Απορρίφθηκε το αίτημα του χρήστη "+requests.get(position).getName(),Toast.LENGTH_SHORT).show();
                            Delete(position);
                        }
                        return;
                    }
                }
                @Override
                public void onFailure(Call<Status_handling> call, Throwable t) {
                    //Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(TripPageCreatorActivity.this,"No Internet",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean CheckInternetConnection(){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
