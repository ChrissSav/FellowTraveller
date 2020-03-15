package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripPageActivity extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.api_url)).addConverterFactory(GsonConverterFactory.create()).build();
    private TripB trip;
    private TextView textView_status;
    private TextView textView_creator_name;
    private EditText textView_from;
    private EditText textView_to;
    private TextView textView_date;
    private TextView textView_time;
    private TextView textView_seats;
    private TextView textView_rate;
    private TextView textView_suitcases;
    private TextView textView_description;
    private TextView textView_price;
    private CircleImageView img;
    private Button select,back,messega_btn;
    private boolean flag;
    private CheckBox bag;
    private List<UserB> passengers ;
    private RecyclerView mRecyclerViewPassengers;
    private PassengerAdapter mAdapterPassengers;
    private RecyclerView.LayoutManager mLayoutManagerP;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);
        final Intent intent = getIntent();
        globalClass = (GlobalClass) getApplicationContext();
        trip =  intent.getParcelableExtra("Trip");
        flag =  intent.getBooleanExtra("F", true);
        passengers = trip.getPassengers();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        img = findViewById(R.id.tripPage_textView_adminImage);
        messega_btn = findViewById(R.id.tripPage_button_sendMessage);
        bag= findViewById(R.id.tripPage_checkBox_bag);
        textView_rate= findViewById(R.id.tripPage_textView_rate);
        textView_status = findViewById(R.id.tripPage_textView_status);
        textView_creator_name = findViewById(R.id.tripPage_textView_creator_name);
        textView_from  = findViewById(R.id.tripPage_textView_from);
        textView_to  = findViewById(R.id.tripPage_textView_to);
        textView_date  = findViewById(R.id.tripPage_textView_date);
        textView_time  = findViewById(R.id.tripPage_textView_time);
        textView_seats  = findViewById(R.id.tripPage_textView_seats);
        textView_suitcases  = findViewById(R.id.tripPage_textView_suitcases);
        textView_description  = findViewById(R.id.tripPage_textView_description);
        textView_price  = findViewById(R.id.tripPage_textView_price);
        select = findViewById(R.id.tripPage_button_select);
        back = findViewById(R.id.tripPage_button_back);


        textView_rate.setText(trip.getCreator().getRate()+"");
        if(!trip.getCreator().getPicture().equals("null")){
            img.setImageBitmap(StringToBitMap(trip.getCreator().getPicture()));
        }

        if(!flag) {
            bag.setVisibility(View.GONE);
            select.setVisibility(View.GONE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TripPageActivity.this,UsersProfileActivity.class);
                intent1.putExtra("User_id",trip.getCreator().getId());
                startActivity(intent1);
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bag.isChecked()){
                    if(trip.getCurrent_num_of_bags()+1<=trip.getMax_bags()){
                        Register("yes");
                    }else{
                        if(trip.getMax_bags()==0) {
                            Toast.makeText(TripPageActivity.this, "Δεν υπάρχει η δυνατότητα μεταφοράς αποσκεύης! ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(TripPageActivity.this, "Ο χώρος των αποσεκύων ειναι γεμάτος! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else {
                    Register("no");
                }

            }
        });
        messega_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(TripPageActivity.this, ChatConversation.class);
                ii.putExtra("id", String.valueOf(trip.getCreator().getId()));
                startActivity(ii);
            }
        });

        FillFields();
        buildRecyclerViewPassengers();

    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void buildRecyclerViewPassengers() {
        if(passengers.size()!=0) {
            mRecyclerViewPassengers = findViewById(R.id.tripPage_RecyclerView_passengers);
            mRecyclerViewPassengers.setHasFixedSize(true);
            mLayoutManagerP = new LinearLayoutManager(TripPageActivity.this);
            mAdapterPassengers = new PassengerAdapter(passengers);
            mRecyclerViewPassengers.setLayoutManager(mLayoutManagerP);
            mRecyclerViewPassengers.setAdapter(mAdapterPassengers);
            mAdapterPassengers.setOnItemClickListener(new PassengerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(TripPageActivity.this,UsersProfileActivity.class);
                    intent.putExtra("User_id",passengers.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }


    public void FillFields(){
        String status = trip.getState();
        if(status.equals("available")){
            textView_status.setText("Σε εξέλιξη");
        }
        else {
            textView_status.setText("Ολοκληρώθηκε");
            select.setEnabled(false);
        }
        textView_creator_name.setText(trip.getCreator().getName());
        textView_from.setText(trip.getFfrom());
        textView_to.setText(trip.getTto());
        textView_date.setText(trip.getDate());
        textView_time.setText(trip.getTime());
        textView_seats.setText(trip.getSeatesStatus());
        textView_suitcases.setText(trip.getbagsStatus());
        textView_description.setText(trip.getDescription());
        textView_price.setText(trip.getPrice()+" ευρώ");
    }


    public void Register(String bag){
        Call<Status_handling> call = jsonPlaceHolderApi.sendRequest(globalClass.getId(),bag,trip.getCreator().getId(),trip.getId());
        call.enqueue(new Callback<Status_handling>() {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TripPageActivity.this,"response "+response.errorBody()+"\n"+"response "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if(status.getStatus().equals("success")){
                    Toast.makeText(TripPageActivity.this,"Επιτυχής επιλογή",Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result",  getIntent().getIntExtra("position",-1));
                    setResult(RESULT_OK, resultIntent);
                    finish();
                    return;
                }
                Toast.makeText(TripPageActivity.this,"Ανεπιτυχής επιλογή",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {
                Toast.makeText(TripPageActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
