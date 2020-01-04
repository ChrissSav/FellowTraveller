package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewSearchOffersActivity extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Button btn_filter,btn_back,btn_refresh;
    private Search_item_filter filter_items;
    private TextInputEditText date_from;
    private TextInputEditText date_to;
    private DatePickerDialog.OnDateSetListener mDateListener1;
    private DatePickerDialog.OnDateSetListener mDateListener2;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TripB> ListOfTrips ;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private String KEY;
    private Spinner sort_spinner;
    private int id;
    private ConstraintLayout constraintLayout;
    private TextView textView;
    private ConstraintSet constraintSetOld = new ConstraintSet();
    private ConstraintSet constraintSetNew = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_offers);
        KEY = getString(R.string.api_key);
        loadUserId();


        ListOfTrips = new ArrayList<>();
        Intent intent = getIntent();
        filter_items = intent.getParcelableExtra("Filter");
        mRecyclerView = findViewById(R.id.ViewSearchOffersActivity_RecyclerView_items);
        btn_filter = findViewById(R.id.ViewSearchOffersActivity_button_filters);
        btn_back = findViewById(R.id.ViewSearchOffersActivity_button_back);
        btn_refresh = findViewById(R.id.ViewSearchOffersActivity_button_refresh);
        sort_spinner = findViewById(R.id.ViewSearchOffersActivity_spinner_sort);
        autoCompleteTextViewFrom = findViewById(R.id.ViewSearchOffersActivity_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(ViewSearchOffersActivity.this, android.R.layout.simple_list_item_1, KEY));
        autoCompleteTextViewTo = findViewById(R.id.ViewSearchOffersActivity_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(ViewSearchOffersActivity.this, android.R.layout.simple_list_item_1, KEY));
        textView = findViewById(R.id.textView8);
        constraintLayout = findViewById(R.id.layout);

        constraintSetOld.clone(constraintLayout);
        constraintSetNew.clone(this, R.layout.activity_view_search_offers_alt);

        date_from = findViewById(R.id.ViewSearchOffersActivity_editText_date_from);
        date_to = findViewById(R.id.ViewSearchOffersActivity_editText_date_to);


        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSearchOffersActivity.this, SearchFiltersActivity.class);
                intent.putExtra("Filter",filter_items);
                startActivityForResult(intent, 2);
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
                getTrips();
            }
        });
        autoCompleteTextViewFrom.clearFocus();
        autoCompleteTextViewTo.clearFocus();



        FillFields();
        getTrips();
        OutFocus();

        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        ViewSearchOffersActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();

            }
        });

        mDateListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mon, d;
                if (month <= 9) {
                    mon = "0" + month;
                } else {
                    mon = month + "";
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = day + "";
                }
                String date = d + "/" + mon + "/" + year;
                date_from.setText(date + "");
            }
        };



        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutFocus();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        ViewSearchOffersActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }

        });


        mDateListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mon, d;
                if (month <= 9) {
                    mon = "0" + month;
                } else {
                    mon = month + "";
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = day + "";
                }
                String date = d + "/" + mon + "/" + year;
                date_to.setText(date + "");
            }
        };

        date_from.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                date_from.setCursorVisible(false);
                date_from.setText(null);
                return true;
            }
        });


        date_to.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                date_to.setCursorVisible(false);
                date_to.setText(null);
                return true;
            }
        });


        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0 && ListOfTrips.size()>1) {
                    Collections.sort(ListOfTrips, TripB.DateComparator);
                    mAdapter.notifyDataSetChanged();
                }
                if(position==1) {
                    Collections.sort(ListOfTrips, TripB.PriceComparatorLowFirst);
                    mAdapter.notifyDataSetChanged();
                }
                if(position==2) {
                    Collections.sort(ListOfTrips, TripB.PriceComparatorΗigherFirst);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Nothing
            }

        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("makismakis","dy dy dy "+dy);
                if (dy > 0) {
                    Transition changeBounds = new ChangeBounds();
                    changeBounds.setInterpolator(new OvershootInterpolator());
                    TransitionManager.beginDelayedTransition(constraintLayout, changeBounds);
                    constraintSetNew.applyTo(constraintLayout);
                } else {
                    Transition changeBounds = new ChangeBounds();
                    changeBounds.setInterpolator(new OvershootInterpolator());
                    TransitionManager.beginDelayedTransition(constraintLayout, changeBounds);
                    constraintSetOld.applyTo(constraintLayout);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Log.i("makismakis","SCROLL_STATE_FLING");
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    Log.i("makismakis","SCROLL_STATE_TOUCH_SCROLL");
                } else {
                    Log.i("mRecyclerView","else  " +mRecyclerView.computeVerticalScrollRange()+" "+mRecyclerView.computeVerticalScrollOffset());
                    if(mRecyclerView.computeVerticalScrollOffset()==0){
                        //Log.i("mRecyclerView","offset  " +(mRecyclerView.computeVerticalScrollOffset()==0)+" "+mRecyclerView.computeVerticalScrollOffset());
                        Transition changeBounds = new ChangeBounds();
                        changeBounds.setInterpolator(new OvershootInterpolator());
                        TransitionManager.beginDelayedTransition(constraintLayout, changeBounds);
                        constraintSetOld.applyTo(constraintLayout);
                    }
                }
            }
        });

    }

    public void FillFields(){
        autoCompleteTextViewFrom.setText(filter_items.getFrom());
        autoCompleteTextViewTo.setText(filter_items.getTo());
        if(date_from.getText().toString().trim()=="0")
            date_from.setText(filter_items.getDate_from());
        if(date_to.getText().toString().trim()=="0")
            date_to.setText(filter_items.getDate_to());


    }

    public void buildRecyclerView() {
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

    private void getTrips() {
        RefreshFilter();
        ListOfTrips.clear();
        Log.i("RefreshFilter", "To : "+filter_items.getDate_to());
        Log.i("RefreshFilter", "From : "+filter_items.getDate_from());
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        Call<List<TripB>> call = jsonPlaceHolderApi.getTripsfilter(filter_items.getFrom(),filter_items.getTo(),filter_items.getDate_from(),filter_items.getDate_to(),filter_items.getTime_from(),filter_items.getTime_to(),
                filter_items.getSeats_from(),filter_items.getSeats_to(),filter_items.getBags_from(),filter_items.getBags_to(),
                filter_items.getRate_from(),filter_items.getRate_to(),filter_items.getPrice_from(),filter_items.getPrice_to(),id);



        Log.i("RefreshFilter", "fefewlkfheugfewhewg : "+call.request().url());

        //Toast.makeText(ViewSearchOffersActivity.this,call.request().url().toString(),Toast.LENGTH_SHORT).show();
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
                int result = data.getIntExtra("result", -1);
                Log.i("resultCode","result :" +result);
                if(result!=(-1)) {
                    Log.i("resultCode"," if result :" +result);
                    ListOfTrips.remove(result);
                    mAdapter.notifyDataSetChanged();
                }
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                filter_items = data.getParcelableExtra("filter");
                if(data.getBooleanExtra("flag",false))
                   getTrips();

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    public void RefreshFilter(){
        String from = autoCompleteTextViewFrom.getText().toString();
        String to = autoCompleteTextViewTo.getText().toString();
        filter_items.setFrom(from);
        filter_items.setTo(to);
        if(!date_from.getText().toString().trim().isEmpty())
            filter_items.setDate_from(date_from.getText().toString());
        else{
            filter_items.setDate_from(0+"");
        }
        if(!date_to.getText().toString().trim().isEmpty())
            filter_items.setDate_to(date_to.getText().toString());
        else{
            filter_items.setDate_to(0+"");
        }
    }


    public void loadUserId() {
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
}

