package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewSearchOffersActivity extends AppCompatActivity {

    private Button filter,back;
    private Search_item_filter filter_items;
    private TextInputEditText date_from;
    private TextInputEditText date_to;
    private TextInputEditText time_from;
    private TextInputEditText time_to;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;

    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Trip> Listoftrips ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KEY = getString(R.string.api_key);
        setContentView(R.layout.activity_view_search_offers);
        Intent intent = getIntent();
        filter_items = intent.getParcelableExtra("Filter");
        filter = findViewById(R.id.ViewSearchOffersActivity_button_filters);
        back = findViewById(R.id.ViewSearchOffersActivity_button_back);

        autoCompleteTextViewFrom = findViewById(R.id.ViewSearchOffersActivity_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(ViewSearchOffersActivity.this, android.R.layout.simple_list_item_1, KEY));
        autoCompleteTextViewTo = findViewById(R.id.ViewSearchOffersActivity_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(ViewSearchOffersActivity.this, android.R.layout.simple_list_item_1, KEY));


        date_from = findViewById(R.id.ViewSearchOffersActivity_editText_date_from);
        date_to = findViewById(R.id.ViewSearchOffersActivity_editText_date_to);

        OutFocus();
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        Listoftrips = new ArrayList<>();
        FillFields();
        getTrips();
        buildRecyclerView();
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSearchOffersActivity.this, SearchFiltersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    public void FillFields(){
        autoCompleteTextViewFrom.setText(filter_items.getFrom());
        autoCompleteTextViewTo.setText(filter_items.getTo());
        date_from.setText(filter_items.getDate_start());
        date_to.setText(filter_items.getDate_end());
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.ViewSearchOffersActivity_RecyclerView_items);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ViewSearchOffersActivity.this);
        mAdapter = new SearchAdapter(Listoftrips);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ViewSearchOffersActivity.this, TripPageActivity.class);
                intent.putExtra("Trip",Listoftrips.get(position));
                startActivity(intent);

            }
        });
    }

    private void getTrips() {

        String from =autoCompleteTextViewFrom.getText().toString();
        String to = autoCompleteTextViewTo.getText().toString();
        Call<List<Trip>> call = jsonPlaceHolderApi.createTripByFilter(from,to);
        call.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> mcall, Response<List<Trip>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewSearchOffersActivity.this,"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Trip> trips = response.body();
                for (int i=0; i<trips.size(); i++){
                    Log.i("RestAPI","i: "+i);
                    Listoftrips.add(trips.get(i));
                }
            }
            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Toast.makeText(ViewSearchOffersActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OutFocus(){
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();
        date_from.clearFocus();
        date_to.clearFocus();
       // time_from.clearFocus();
        //time_to.clearFocus();
    }
}
