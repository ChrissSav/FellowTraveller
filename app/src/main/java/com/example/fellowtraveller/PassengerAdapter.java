package com.example.fellowtraveller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ExampleViewHolder> {
    private List<UserB> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView name,bag,rate;
        private CircleImageView img;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.passenger_item_textView_name);
            bag = itemView.findViewById(R.id.passenger_item_textView_bag);
            rate = itemView.findViewById(R.id.passenger_item_textView_rate);
            img = itemView.findViewById(R.id.passenger_item_CircleImageView_pic);

            img.setOnClickListener(new View.OnClickListener() {
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
        holder.name.setText(currentItem.getName());
        holder.rate.setText(currentItem.getRate()+"");
        if(!currentItem.getPicture().equals("null"))
            holder.img.setImageBitmap(StringToBitMap(currentItem.getPicture()));
        if(currentItem.getBag().equals("no"))
            holder.bag.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}