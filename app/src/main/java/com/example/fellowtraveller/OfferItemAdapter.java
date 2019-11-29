package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfferItemAdapter extends RecyclerView.Adapter<OfferItemAdapter.ExampleViewHolder> {
    private ArrayList<OfferItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView from;
        public TextView to;
        public TextView name;
        public Button btn;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.offer_items_container_textView_from);
            to = itemView.findViewById(R.id.offer_items_container_textView_to);
            name = itemView.findViewById(R.id.offer_items_container_textView_name);
            btn = itemView.findViewById((R.id.offer_items_container_btn_edit));
        }
    }
    //Constructor
    public OfferItemAdapter (ArrayList<OfferItem> exampleList){
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_items, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        OfferItem currentItem = mExampleList.get(position);

        holder.from.setText(currentItem.getFrom());
        holder.to.setText(currentItem.getTo());
        holder.name.setText(currentItem.getName());


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
