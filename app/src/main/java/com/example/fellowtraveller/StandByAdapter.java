package com.example.fellowtraveller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StandByAdapter extends RecyclerView.Adapter<StandByAdapter.ExampleViewHolder> {
    private ArrayList<TripB> mExampleList;




    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView from;
        public TextView to;
        public TextView date_time;
        public TextView rate;
        private TextView price;
        private CircleImageView img;

        public ExampleViewHolder(View itemView) {
            super(itemView);


            date_time = itemView.findViewById(R.id.offer_trip_item2_textView_info);
            from = itemView.findViewById(R.id.offer_trip_item2_from);
            to = itemView.findViewById(R.id.offer_trip_item2_to);
            rate = itemView.findViewById(R.id.offerItem2_user_rating);
            name = itemView.findViewById(R.id.offerItem2_textView_name);
            img = itemView.findViewById(R.id.offerItem2_user_pic);
            price = itemView.findViewById(R.id.offer_trip_item2_textView_price);

        }
    }

    public StandByAdapter(ArrayList<TripB> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_trip_item2, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        TripB currentItem = mExampleList.get(position);
        holder.from.setText(currentItem.getFfrom());
        holder.to.setText(currentItem.getTto());
        holder.name.setText(currentItem.getCreator().getName());
        holder.price.setText(currentItem.getPrice()+" €");
        holder.rate.setText(currentItem.getCreator().getRate()+"");
        if(!currentItem.getCreator().getPicture().equals("null")){
            holder.img.setImageBitmap(StringToBitMap(currentItem.getCreator().getPicture()));
        }
        holder.date_time.setText(currentItem.getDate()+" , "+currentItem.getTime());

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