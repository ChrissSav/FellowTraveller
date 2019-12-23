package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class WriteReviewActivity extends AppCompatActivity {
    private ImageButton reliableHappy,reliableNeutral,reliableSad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);



        SmileRating smileRatingFriendly = (SmileRating)findViewById(R.id.smileRatingFriendly);
        SmileRating smileRatingReliable = (SmileRating)findViewById(R.id.smileRatingReliable);
        SmileRating smileRatingCarefull = (SmileRating)findViewById(R.id.smileRatingCarefull);

        setEmojiNames(smileRatingFriendly);
        setEmojiNames(smileRatingReliable);
        setEmojiNames(smileRatingCarefull);

        smileRatingFriendly.setShowLine(false);
        smileRatingReliable.setShowLine(false);
        smileRatingCarefull.setShowLine(false);

        smileRatingCarefull.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.TERRIBLE:
                        break;
                    case SmileRating.BAD:
                        break;
                    case SmileRating.OKAY:
                        break;
                    case SmileRating.GOOD:
                        break;
                    case SmileRating.GREAT:
                        break;
                }
            }

        });



    }
    public void setEmojiNames(SmileRating item){
        item.setNameForSmile(BaseRating.TERRIBLE,"ΠΟΛΥ ΚΑΚΟΣ");
        item.setNameForSmile(BaseRating.BAD,"ΚΑΚΟΣ");
        item.setNameForSmile(BaseRating.OKAY,"ΜΕΤΡΙΟΣ");
        item.setNameForSmile(BaseRating.GOOD,"ΚΑΛΟΣ");
        item.setNameForSmile(BaseRating.GREAT,"ΠΟΛΥ ΚΑΛΟΣ");
    }
}
