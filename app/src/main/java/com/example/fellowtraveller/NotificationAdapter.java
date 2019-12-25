package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ExampleViewHolder> {
    private ArrayList<Notification> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView description,shape;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            description = itemView.findViewById(R.id.notification_item_description);
            shape = itemView.findViewById(R.id.textView3);
            shape.setOnClickListener(new View.OnClickListener() {
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

    public NotificationAdapter(ArrayList<Notification> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Notification currentItem = mExampleList.get(position);
        if(currentItem.getType().equals("request")) {
            holder.description.setText("Ο χρήστης " + currentItem.getUser().getName() + " σας έκανε αίτημα.");
        }else if(currentItem.getType().equals("accept")){
            holder.description.setText("Ο χρήστης " + currentItem.getTrip().getCreator().getName() + " σας αποδέχτηκε.");
        }else if(currentItem.getType().equals("reject")){
            holder.description.setText("Ο χρήστης " + currentItem.getTrip().getCreator().getName() + " σας απέρριψε.");
        }else if(currentItem.getType().equals("rate")){
            holder.description.setText("Ήρθε η ώρα της αξιολογήσης του χρήστη " + currentItem.getTrip().getCreator().getName());
        }
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}