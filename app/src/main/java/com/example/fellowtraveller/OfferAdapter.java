package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ExampleViewHolder> {

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView from;
        public TextView to;
        public TextView date;
        public TextView time;
        public TextView number_of_passengers;
        public TextView number_of_bags;
        public Button btnEdit, btnRequest;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.offerItem_textView_date);
            time = itemView.findViewById(R.id.offerItem_textView_time);
            from = itemView.findViewById(R.id.offerItem_textView_from);
            to = itemView.findViewById(R.id.offerItem_textView_to);
            name = itemView.findViewById(R.id.offerItem_textView_name);
            number_of_passengers = itemView.findViewById(R.id.offerItem_textView_seats);
            number_of_bags = itemView.findViewById(R.id.offerItem_textView_bags);
            btnEdit = itemView.findViewById((R.id.offerItem_button_select));
            btnRequest = itemView.findViewById((R.id.offerItem_request_button));

        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_trip_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
