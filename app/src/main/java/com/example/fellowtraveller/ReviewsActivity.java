package com.example.fellowtraveller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewsActivity extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ArrayList<ReviewItem> reviewsList;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit ;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Intent intent = getIntent();
        id = intent.getIntExtra("Target_id",0);
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        reviewsList= new ArrayList<>();
        /*reviewsList.add(new ReviewItem("Σπύρος Ράντογλου", "Μάιος 2017", "Πολύ καλός οδηγός, προσέχει πολύ στον δρόμο"));
        reviewsList.add(new ReviewItem("Tyler Joseph", "Ιούνης 2018", "This guy is fantastic.. Hes is a big fun of me and now " +
                "i am a big fun of him.. Reliable and really worth the ride..I suggest him..||-//"));
        reviewsList.add(new ReviewItem("Martijn Garritsen", "Οκτώβρης 2018", "Reliable driver..and of course. First time in Greece" +
                "and not hearing stupid commercial greek songs...hohoho Love electro music"));
        reviewsList.add(new ReviewItem("ΑΛέξης Παπαντωνίου", "Απρίλιος 2018", "Μπορώ να πω έχω μείνει αρκετά ικανοποιημένος από τον οδηγό"));
        reviewsList.add(new ReviewItem("Μελέτης Αργυρόπουλος", "Απρίλιος 2018", "Έχω ξαναταξιδέψει μαζί του, όχι ως οδηγός, αλλός ως " +
                "συνεπιβάτης. Πολύ ευχάριστος γενικά και καλός άνθρωπος"));
        reviewsList.add(new ReviewItem("Γιώργος Φερέτης", "Σεπτέμβιος 2018", "Καλός"));
        reviewsList.add(new ReviewItem("Σωτήρης Σεραφόπουλος", "Απρίλης 2019", "Παιδιά είναι πολύ αξιόπιστος και γενικά το ταξίδι ήταν" +
                "ότι πρέπει.. Και σωστός σε όλα.. Εγκρίνω"));*/

        getReviews();






    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.ReviewsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ReviewsAdapter(reviewsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void getReviews() {
        Call<List<ReviewItem>> call = jsonPlaceHolderApi.getUsersRateAnother(id);
        call.enqueue(new Callback<List<ReviewItem>>() {
            @Override
            public void onResponse(Call<List<ReviewItem>> mcall, Response<List<ReviewItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ReviewsActivity.this,"response "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<ReviewItem> revies = response.body();
                for (int i=0; i<revies.size(); i++){
                    reviewsList.add(revies.get(i));
                }
                buildRecyclerView();

            }
            @Override
            public void onFailure(Call<List<ReviewItem>> call, Throwable t) {
                //Toast.makeText(getActivity(),"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
