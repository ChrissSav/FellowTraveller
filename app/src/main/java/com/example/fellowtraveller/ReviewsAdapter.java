package com.example.fellowtraveller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ExampleViewHolder> {
    private ArrayList<ReviewItem> mReviewsList;
    private ReviewsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    public void setOnItemClickListener(ReviewsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView circleImageView;
        public ImageView rating;
        public TextView name;
        public TextView date;
        public TextView review;
        public TextView num;


        public ExampleViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.reviewer_profile_pic);
            rating = itemView.findViewById(R.id.rating);
            name = itemView.findViewById(R.id.reviewer_name);
            date = itemView.findViewById(R.id.review_date);
            review = itemView.findViewById(R.id.review);
            num = itemView.findViewById(R.id.rating_num);

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public ReviewsAdapter (ArrayList<ReviewItem> reviewsList){
        mReviewsList = reviewsList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ReviewItem curr = mReviewsList.get(position);
        /*holder.nameTV.setText(curr.getName());
        holder.dateTV.setText(curr.getDate());
        holder.reviewTV.setText(curr.getReview());*/
        holder.name.setText(curr.getUser().getName());
        holder.date.setText(SetImgToReliable(curr.getDate()));
        holder.num.setText(round(curr.getRate(),1)+"");
        SetImgToReliable(curr.getDate());
        if(curr.getDescription().equals("") || curr.getDescription().equals(" "))
            holder.review.setText("Δεν υπάρχει περιγραφή");
        else
            holder.review.setText(curr.getDescription());

        if(!curr.getUser().getPicture().equals("null")){
            holder.circleImageView.setImageBitmap(StringToBitMap(curr.getUser().getPicture()));
        }


    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
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
    public String SetImgToReliable(String ddate) {
        String[] parts = ddate.split("-");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        int num = Integer.parseInt(month);
        String teliko = null;
        switch (num){
            case 1:
                teliko = "Ιανουαρίου";
                break;
            case 0:
                teliko =  "Φεβρουαρίου";
                break;
            case 3:
                teliko =  "Μαρτίου";
                break;
            case 4:
                teliko =  "Απριλίου";
                break;
            case 5:
                teliko =  "Μαΐου";
                break;
            case 6:
                teliko =  "Ιουνίου";
                break;
            case 7:
                teliko =  "Ιουλίου";
                break;
            case 8:
                teliko =  "Αυγούστου";
                break;
            case 9:
                teliko =  "Σεπτεμβρίου";
                break;
            case 10:
                teliko =  "Οκτωμβρίου";
                break;
            case 11:
                teliko =  "Νοεμβρίου";
                break;
            case 12:
                teliko =  "Δεκεμβρίου";
                break;
            default:
                break;
        }
        return day+" "+teliko+" "+year;
    }
}
