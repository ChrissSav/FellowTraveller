package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class WriteReviewActivity extends AppCompatActivity {
    private int friendlyScore, reliableScore, carefullScore;
    private TextView textView;
    private Button btn;
    private int overallScore;
    private ImageButton imageButtonEdit,imageButtonAccept,imageButtonCancel;
    private TextView textViewReview;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        textView = findViewById(R.id.review_text);
        btn = findViewById(R.id.submit_rate_btn);


        SmileRating smileRatingFriendly = (SmileRating) findViewById(R.id.smileRatingFriendly);
        SmileRating smileRatingReliable = (SmileRating) findViewById(R.id.smileRatingReliable);
        SmileRating smileRatingCarefull = (SmileRating) findViewById(R.id.smileRatingCarefull);

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendlyScore == 0) {
                    textView.setText("Δεν αξιολογήθηκε ο χρήστης ως προς την φιλικότητα του");
                } else {
                    textView.setText("Ο χρήτης αξιολογήθηκε ως προς την φιλικότητα του με " + friendlyScore + " αστέρια"+"       Συνολικό score " + overallScore);
                }

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
}