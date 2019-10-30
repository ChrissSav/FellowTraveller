package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.ExampleViewHolder> {

    private ArrayList<SearchItemscontainer> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);


    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView from;
        public TextView to;
        public TextView number;
        public TextView name;
        private Button btn;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            from = itemView.findViewById(R.id.search_items_container_textView_date);
            from = itemView.findViewById(R.id.search_items_container_textView_from);
            to = itemView.findViewById(R.id.search_items_container_textView_to);
            number = itemView.findViewById(R.id.search_items_container_textView_number);
            name = itemView.findViewById(R.id.search_items_container_textView_name);
            btn = itemView.findViewById(R.id.search_items_container_btn_approved);



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

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items_container, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        SearchItemscontainer currentItem = mExampleList.get(position);
        holder.date.setText(currentItem.getDate());
        holder.from.setText(currentItem.getFrom());
        holder.to.setText(currentItem.getTo());
        holder.name.setText(currentItem.getName());
        holder.number.setText(currentItem.getNumber());



    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
