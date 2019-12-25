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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteReviewActivity extends AppCompatActivity {
    private int friendlyScore, reliableScore, carefullScore;
    private TextView textView;
    private Button btn_submit;
    private int overallScore;
    private ImageButton imageButtonEdit,imageButtonAccept,imageButtonCancel;
    private TextView textViewReview;
    private EditText editText;
    private SmileRating smileRatingFriendly;
    private SmileRating smileRatingReliable;
    private SmileRating smileRatingCarefull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        textView = findViewById(R.id.review_text);
        btn_submit = findViewById(R.id.submit_rate_btn);


        smileRatingFriendly = (SmileRating) findViewById(R.id.smileRatingFriendly);
        smileRatingReliable = (SmileRating) findViewById(R.id.smileRatingReliable);
        smileRatingCarefull = (SmileRating) findViewById(R.id.smileRatingCarefull);

        imageButtonEdit = findViewById(R.id.edit_button);
        imageButtonAccept = findViewById(R.id.button_accept);
        imageButtonCancel = findViewById(R.id.button_cancel);

        editText = (EditText) findViewById(R.id.editText);
        textViewReview = findViewById(R.id.textView_review);

        setEmojiNames(smileRatingFriendly);
        setEmojiNames(smileRatingReliable);
        setEmojiNames(smileRatingCarefull);

        smileRatingFriendly.setShowLine(false);
        smileRatingReliable.setShowLine(false);
        smileRatingCarefull.setShowLine(false);

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
        smileRatingCarefull.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                carefullScore = level;
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
                Log.i("Rate","friendlyScore :"+friendlyScore);
                Log.i("Rate","reliableScore :"+reliableScore);
                Log.i("Rate","carefullScore :"+carefullScore);
                Log.i("Rate", "editText :"+editText.length());

                if(CheckRate()){
                    //RegisterRateUser();
                }
               /* if (friendlyScore == 0) {
                    textView.setText("Δεν αξιολογήθηκε ο χρήστης ως προς την φιλικότητα του");
                } else {
                    textView.setText("Ο χρήτης αξιολογήθηκε ως προς την φιλικότητα του με " + friendlyScore + " αστέρια"+"       Συνολικό score " + overallScore);
                }*/

            }
        });

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
        if(carefullScore<1) {
            Toast.makeText(WriteReviewActivity.this,"Δεν αξιολογήθηκε ο χρήστης ως προς την προσεκτικότητα του",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    public int getOverall(int a,int b, int c){
        int overall = 0;
        int count = 0;

        if(a > 0){
            overall = overall + a;
            count++;
        }
        if(b > 0){
            overall = overall + b;
            count++;
        }
        if(c > 0){
            overall = overall + c;
            count++;
        }
        if(count>0){
            overall = overall / count;
        }

        return overall;
    }


    private void RegisterRateUser(){
        JsonApi jsonPlaceHolderApi;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://snf-871339.vm.okeanos.grnet.gr:5000/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonApi.class);
        Call<Status_handling> call = jsonPlaceHolderApi.RegisterRate(friendlyScore, reliableScore, carefullScore,editText.getText().toString());
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
                    Intent intent = new Intent(WriteReviewActivity.this, HomeBetaActivity.class);
                    startActivity(intent);
                    finish();
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
}