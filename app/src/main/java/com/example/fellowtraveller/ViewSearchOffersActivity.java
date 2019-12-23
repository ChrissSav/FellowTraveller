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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewSearchOffersActivity extends AppCompatActivity {

    private Button btn_filter,btn_back,btn_refresh;
    private Search_item_filter filter_items;
    private TextInputEditText date_from;
    private TextInputEditText date_to;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TripB> ListOfTrips ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KEY = getString(R.string.api_key);
        setContentView(R.layout.activity_view_search_offers);
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        ListOfTrips = new ArrayList<>();
        Intent intent = getIntent();
        filter_items = intent.getParcelableExtra("Filter");
        btn_filter = findViewById(R.id.ViewSearchOffersActivity_button_filters);
        btn_back = findViewById(R.id.ViewSearchOffersActivity_button_back);
        btn_refresh = findViewById(R.id.ViewSearchOffersActivity_button_refresh);

        autoCompleteTextViewFrom = findViewById(R.id.ViewSearchOffersActivity_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(ViewSearchOffersActivity.this, android.R.layout.simple_list_item_1, KEY));
        autoCompleteTextViewTo = findViewById(R.id.ViewSearchOffersActivity_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(ViewSearchOffersActivity.this, android.R.layout.simple_list_item_1, KEY));


        date_from = findViewById(R.id.ViewSearchOffersActivity_editText_date_from);
        date_to = findViewById(R.id.ViewSearchOffersActivity_editText_date_to);


        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSearchOffersActivity.this, SearchFiltersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListOfTrips = new ArrayList<>();
                getTrips(autoCompleteTextViewFrom,autoCompleteTextViewTo);
            }
        });
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();



        FillFields();
        OutFocus();
        getTrips(autoCompleteTextViewFrom,autoCompleteTextViewTo);







    }

    public void FillFields(){
        autoCompleteTextViewFrom.setText(filter_items.getFrom());
        autoCompleteTextViewTo.setText(filter_items.getTo());
        date_from.setText(filter_items.getDate_from());
        date_to.setText(filter_items.getDate_to());
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.ViewSearchOffersActivity_RecyclerView_items);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ViewSearchOffersActivity.this);
        mAdapter = new SearchAdapter(ListOfTrips);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ViewSearchOffersActivity.this, TripPageActivity.class);
                intent.putExtra("Trip",ListOfTrips.get(position));
                intent.putExtra("position",position);
                startActivityForResult(intent, 1);

            }
        });
    }

    private void getTrips(AutoCompleteTextView autoCompleteTextViewFrom,AutoCompleteTextView autoCompleteTextViewTo) {
        Log.i("RestAPI trip","mpika");
        String from =autoCompleteTextViewFrom.getText().toString();
        String to = autoCompleteTextViewTo.getText().toString();
        String dateFrom,dateTo;
        if(date_from.getText().toString().trim().isEmpty())
            dateFrom =0+"";
        else{
            dateFrom = date_from.getText().toString();
        }
        if(date_to.getText().toString().trim().isEmpty())
            dateTo =0+"";
        else{
            dateTo = date_to.getText().toString();
        }

        Call<List<TripB>> call = jsonPlaceHolderApi.getTripsfilter(from,to,dateFrom,dateTo,filter_items.getTime_from(),filter_items.getTime_to(),
                filter_items.getSeats_from(),filter_items.getSeats_to(),filter_items.getBags_from(),filter_items.getBags_to(),
                filter_items.getRate_from(),filter_items.getRate_to(),filter_items.getPrice_from(),filter_items.getPrice_to());
        call.enqueue(new Callback<List<TripB>>() {
            @Override
            public void onResponse(Call<List<TripB>> mcall, Response<List<TripB>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewSearchOffersActivity.this,"responseb "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TripB> trips = response.body();
                for (int i=0; i<trips.size(); i++){
                    Log.i("RestAPI trip","i: "+trips.get(i).getFfrom());
                    ListOfTrips.add(trips.get(i));
                }
                buildRecyclerView();
            }
            @Override
            public void onFailure(Call<List<TripB>> call, Throwable t) {
                Toast.makeText(ViewSearchOffersActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OutFocus(){
        TextInputLayout t;
        t = findViewById(R.id.textInputLayout7);
        t.clearFocus();
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();
        date_from.clearFocus();
        date_to.clearFocus();
       // time_from.clearFocus();
        //time_to.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                ListOfTrips.remove(result);
                mAdapter.notifyDataSetChanged();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
