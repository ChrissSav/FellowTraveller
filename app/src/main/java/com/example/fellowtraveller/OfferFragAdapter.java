package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfferFragAdapter extends RecyclerView.Adapter<OfferFragAdapter.ExampleViewHolder> {
    private ArrayList<TripB> mExampleList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView from;
        public TextView to;
        public TextView date;
        public TextView time;
        public TextView number_of_passengers;
        public TextView number_of_bags;
        public Button btnEdit, btnReq;
        public CircleImageView circleImageView;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            date = itemView.findViewById(R.id.offerItem_textView_date);
            time = itemView.findViewById(R.id.offerItem_textView_time);
            from = itemView.findViewById(R.id.offerItem_textView_from);
            to = itemView.findViewById(R.id.offerItem_textView_to);
            name = itemView.findViewById(R.id.offerItem_textView_name);
            number_of_passengers = itemView.findViewById(R.id.offerItem_textView_seats);
            number_of_bags = itemView.findViewById(R.id.offerItem_textView_bags);
            circleImageView = itemView.findViewById(R.id.profile_picture);
            btnEdit = itemView.findViewById(R.id.offerItem_button_select);
            btnReq = itemView.findViewById(R.id.offerItem_request_button);

            btnEdit.setOnClickListener(new View.OnClickListener() {
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

    public OfferFragAdapter(ArrayList<TripB> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_trip_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        TripB currentItem = mExampleList.get(position);
        holder.from.setText(currentItem.getFfrom());
        holder.to.setText(currentItem.getTto());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
        holder.name.setText(currentItem.getCreator().getName());
        holder.number_of_passengers.setText(currentItem.getSeatesStatus()+"");
        holder.number_of_bags.setText(currentItem.getbagsStatus()+"");
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}