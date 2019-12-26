package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ExampleViewHolder> {
    private ArrayList<ReviewItem> mReviewsList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView circleImageView;
        public ImageView rating;
        public TextView name;
        public TextView date;
        public TextView review;
        public TextView num;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.reviewer_profile_pic);
            rating = itemView.findViewById(R.id.rating);
            name = itemView.findViewById(R.id.reviewer_name);
            date = itemView.findViewById(R.id.review_date);
            review = itemView.findViewById(R.id.review);
            num = itemView.findViewById(R.id.rating_num);
        }
    }
    public ReviewsAdapter (ArrayList<ReviewItem> reviewsList){
        mReviewsList = reviewsList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ReviewItem curr = mReviewsList.get(position);
        /*holder.nameTV.setText(curr.getName());
        holder.dateTV.setText(curr.getDate());
        holder.reviewTV.setText(curr.getReview());*/
        holder.name.setText(curr.getUser().getName());
        holder.date.setText(curr.getDate());
        holder.num.setText(round(curr.getRate(),1)+"");
        if(curr.getDescription().equals("") || curr.getDescription().equals(" "))
            holder.review.setText("Δεν υπάρχει περιγραφή");
        else
            holder.review.setText(curr.getDescription());




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
}
