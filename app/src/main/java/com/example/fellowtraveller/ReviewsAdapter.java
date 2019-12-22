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
        public TextView nameTV;
        public TextView dateTV;
        public TextView reviewTV;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.reviewer_profile_pic);
            rating = itemView.findViewById(R.id.rating);
            nameTV = itemView.findViewById(R.id.reviewer_name);
            dateTV = itemView.findViewById(R.id.review_date);
            reviewTV = itemView.findViewById(R.id.review);
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

        holder.nameTV.setText(curr.getName());
        holder.dateTV.setText(curr.getDate());
        holder.reviewTV.setText(curr.getReview());



    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }
}
