package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ExampleViewHolder> {
    private List<UserB> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position, int flag);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView name,bag;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.passenger_item_textView_name);
            bag = itemView.findViewById(R.id.passenger_item_textView_bag);
        }
    }

    public PassengerAdapter(List<UserB> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        UserB currentItem = mExampleList.get(position);
        holder.name.setText(currentItem.getName());
        if(currentItem.getBag().equals("no"))
            holder.bag.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}