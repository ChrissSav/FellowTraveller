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
        public TextView from;
        public TextView to;
        public TextView name;
        public TextView number;
        public Button btn;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            from = itemView.findViewById(R.id.search_items_container_textView_from);
            to = itemView.findViewById(R.id.search_items_container_textView_to);
            name = itemView.findViewById(R.id.search_items_container_textView_name);
            number = itemView.findViewById(R.id.search_items_container_textView_number);
            btn = itemView.findViewById((R.id.search_items_container_btn_approved));

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searches_items, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Trip currentItem = mExampleList.get(position);

        holder.from.setText(currentItem.getFrom());
        holder.to.setText(currentItem.getTo());
        holder.name.setText(currentItem.getCreator().getName());
        holder.number.setText(currentItem.getNumOfPassengers()+"");
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}