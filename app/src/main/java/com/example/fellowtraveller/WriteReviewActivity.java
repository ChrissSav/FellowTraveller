package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteReviewActivity extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private int friendlyScore, reliableScore, carefulScore,consistentScore;
    private TextView textView;
    private Button btn_submit;
    private ImageButton imageButtonEdit,imageButtonAccept,imageButtonCancel;
    private TextView textViewReview;
    private EditText editText;
    private TextView username;
    private SmileRating smileRatingFriendly;
    private SmileRating smileRatingReliable;
    private SmileRating smileRatingCareful;
    private SmileRating smileRatingConsistent;
    private int id;
    private JsonApi jsonPlaceHolderApi;
    private Retrofit retrofit;
    private TripB trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        trip =  intent.getParcelableExtra("Trip");
        retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        setContentView(R.layout.activity_write_review);


        username = findViewById(R.id.WriteReviewActivity_userName);
        textView = findViewById(R.id.review_text);
        btn_submit = findViewById(R.id.submit_rate_btn);

        username.setText(trip.getCreator().getName());
        smileRatingFriendly = (SmileRating) findViewById(R.id.smileRatingFriendly);
        smileRatingReliable = (SmileRating) findViewById(R.id.smileRatingReliable);
        smileRatingCareful = (SmileRating) findViewById(R.id.smileRatingCarefull);
        smileRatingConsistent =findViewById(R.id.smileRatingConsistent);

        imageButtonEdit = findViewById(R.id.edit_button);
        imageButtonAccept = findViewById(R.id.button_accept);
        imageButtonCancel = findViewById(R.id.button_cancel);

        editText = (EditText) findViewById(R.id.editText);
        textViewReview = findViewById(R.id.textView_review);

        setEmojiNames(smileRatingFriendly);
        setEmojiNames(smileRatingReliable);
        setEmojiNames(smileRatingCareful);

        smileRatingFriendly.setShowLine(false);
        smileRatingReliable.setShowLine(false);
        smileRatingCareful.setShowLine(false);
        smileRatingConsistent.setShowLine(false);

        imageButtonAccept.setVisibility(View.GONE);
        imageButtonCancel.setVisibility(View.GONE);

        smileRatingFriendly.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                friendlyScore = level;
            }
        });

        smileRatingReliable.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                reliableScore = level;
            }
        });
        smileRatingCareful.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                carefulScore = level;
            }
        });
        smileRatingConsistent.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                consistentScore = level;
            }
        });
        //overallScore = getOverall(friendlyScore, reliableScore, carefullScore );

        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewReview.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);

                imageButtonEdit.setVisibility(View.GONE);

                imageButtonAccept.setVisibility(View.VISIBLE);
                imageButtonCancel.setVisibility(View.VISIBLE);

                imageButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textViewReview.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);

                        imageButtonEdit.setVisibility(View.VISIBLE);

                        imageButtonAccept.setVisibility(View.GONE);
                        imageButtonCancel.setVisibility(View.GONE);
                    }
                });

                imageButtonAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String aboutInfo = editText.getText().toString();
                        if(aboutInfo != null && !aboutInfo.isEmpty())
                        {
                            textViewReview.setText(aboutInfo);
                        }

                        textViewReview.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);

                        imageButtonEdit.setVisibility(View.VISIBLE);

                        imageButtonAccept.setVisibility(View.GONE);
                        imageButtonCancel.setVisibility(View.GONE);
                    }
                });
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckRate()){
                    RegisterRateUser();
                }
            }
        });

        loadUserInfo();

    }

    public void setEmojiNames(SmileRating item) {
        item.setNameForSmile(BaseRating.TERRIBLE, "ΠΟΛΥ ΚΑΚΟΣ");
        item.setNameForSmile(BaseRating.BAD, "ΚΑΚΟΣ");
        item.setNameForSmile(BaseRating.OKAY, "ΜΕΤΡΙΟΣ");
        item.setNameForSmile(BaseRating.GOOD, "ΚΑΛΟΣ");
        item.setNameForSmile(BaseRating.GREAT, "ΠΟΛΥ ΚΑΛΟΣ");
    }


    //friendlyScore, reliableScore, carefullScore
    public boolean CheckRate(){
        if(friendlyScore<1) {
            Toast.makeText(WriteReviewActivity.this,"Δεν αξιολογήθηκε ο χρήστης ως προς την φιλικότητα του",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(reliableScore<1) {
            Toast.makeText(WriteReviewActivity.this,"Δεν αξιολογήθηκε ο χρήστης ως προς την αξιοπιστία του",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(carefulScore<1) {
            Toast.makeText(WriteReviewActivity.this,"Δεν αξιολογήθηκε ο χρήστης ως προς την προσεκτικότητα του",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(consistentScore<1) {
            Toast.makeText(WriteReviewActivity.this,"Δεν αξιολογήθηκε ο χρήστης ως προς την συνέπεια του",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





    private void RegisterRateUser(){

        Call<Status_handling> call = jsonPlaceHolderApi.RegisterRate(id,trip.getCreator().getId(),friendlyScore, reliableScore,
                carefulScore,consistentScore,editText.getText().toString()+" ");
        call.enqueue(new Callback<Status_handling>() {
            @Override
            public void onResponse(Call<Status_handling> mcall, Response<Status_handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(WriteReviewActivity.this,"response "+response.errorBody()+"\n"+"response "+response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_handling status = response.body();
                if(status.getStatus().equals("success")){
                    Toast.makeText(WriteReviewActivity.this,"Ο χρήτης αξιολογήθηκε με επιτυχία",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return;
                }
                Toast.makeText(WriteReviewActivity.this,"Ανεπιτυχής είσοδος",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Status_handling> call, Throwable t) {

                // Toast.makeText(LoginActivity.this,"t: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
                    Log.i("loadUserInfo","id: "+id);
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