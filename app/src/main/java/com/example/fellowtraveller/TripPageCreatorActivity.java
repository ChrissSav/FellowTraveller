package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

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
    private TextView textView_suitcases;
    private Button back;
    private List<UserB> requests ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;


    private RecyclerView mRecyclerView;
    private RequestAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page_creator);
        Intent intent = getIntent();
        trip = intent.getParcelableExtra("Trip");
        requests = trip.getRequests();
        textView_status = findViewById(R.id.TripPageCreatorActivity_textView_status);
        textView_creator_name = findViewById(R.id.TripPageCreatorActivity_textView_creator_name);
        textView_from  = findViewById(R.id.TripPageCreatorActivity_textView_from);
        textView_to  = findViewById(R.id.TripPageCreatorActivity_textView_to);
        textView_date  = findViewById(R.id.TripPageCreatorActivity_textView_date);
        textView_time  = findViewById(R.id.TripPageCreatorActivity_textView_time);
        textView_seats  = findViewById(R.id.TripPageCreatorActivity_textView_seats);
        textView_suitcases  = findViewById(R.id.TripPageCreatorActivity_textView_suitcases);
        back = findViewById(R.id.TripPageCreatorActivity_button_back);
        FillFields();
       /* User user = new User(96,"Makis","22-02-2019","uygygy","uygyy","6934567891",2.0,0,0);

        requests = new ArrayList<>();
        requests.add(user);
        user = new User(96,"Takis","22-02-2019","uygygy","uygyy","6934567891",2.0,0,0);
        requests.add(user);*/
       // FillFields();
        buildRecyclerView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                    Toast.makeText(TripPageCreatorActivity.this,"Εγκρίθηκε το αίτημα του χρήστη "+requests.get(position).getName(),Toast.LENGTH_SHORT).show();
                    Delete(position);
                    int num = trip.getCurrent_num_of_seats()+1;
                    trip.setCurrent_num_of_seats(num);
                    textView_seats.setText(trip.getSeatesStatus());
                }//flag==1 reject
                else if(flag == 1){
                    Toast.makeText(TripPageCreatorActivity.this,"Απορρίφθηκε το αίτημα του χρήστη "+requests.get(position).getName(),Toast.LENGTH_SHORT).show();
                    Delete(position);
                }
            }
        });
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
        textView_suitcases.setText(trip.getbagsStatus());
    }





}
