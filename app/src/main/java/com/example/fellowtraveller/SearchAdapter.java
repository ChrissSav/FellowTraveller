package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ExampleViewHolder> {
    private ArrayList<Trip> mExampleList;
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
        public Button btn;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            /*from = itemView.findViewById(R.id.search_items_container_textView_from);
            to = itemView.findViewById(R.id.search_items_container_textView_to);
            name = itemView.findViewById(R.id.search_items_container_textView_name);
            number = itemView.findViewById(R.id.search_items_container_textView_number);
            btn = itemView.findViewById((R.id.search_items_container_btn_approved));*/
            date = itemView.findViewById(R.id.searchItem_textView_date);
            time = itemView.findViewById(R.id.searchItem_textView_time);
            from = itemView.findViewById(R.id.searchItem_textView_from);
            to = itemView.findViewById(R.id.searchItem_textView_to);
            name = itemView.findViewById(R.id.searchItem_textView_name);
            number_of_passengers = itemView.findViewById(R.id.searchItem_textView_seats);
            number_of_bags = itemView.findViewById(R.id.searchItem_textView_bags);
            btn = itemView.findViewById((R.id.searchItem_button_select));

            btn.setOnClickListener(new View.OnClickListener() {
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

    public SearchAdapter(ArrayList<Trip> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_trip_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Trip currentItem = mExampleList.get(position);
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