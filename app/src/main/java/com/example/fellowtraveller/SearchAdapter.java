package com.example.fellowtraveller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ExampleViewHolder> {
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
        public TextView time,rate;
        public TextView number_of_passengers;
        public TextView number_of_bags;
        private TextView price;
        private CircleImageView img;
        public Button btn;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);


            date = itemView.findViewById(R.id.searchItem_textView_date);
            time = itemView.findViewById(R.id.searchItem_textView_time);
            from = itemView.findViewById(R.id.searchItem_textView_from);
            to = itemView.findViewById(R.id.searchItem_textView_to);
            rate = itemView.findViewById(R.id.searchItem_textView_rate);
            name = itemView.findViewById(R.id.searchItem_textView_name);
            number_of_passengers = itemView.findViewById(R.id.searchItem_textView_seats);
            number_of_bags = itemView.findViewById(R.id.searchItem_textView_bags);
            btn = itemView.findViewById(R.id.searchItem_button_select);
            img = itemView.findViewById(R.id.searchItem_user_pic);
            price = itemView.findViewById(R.id.searchItem_textView_price);
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

    public SearchAdapter(ArrayList<TripB> exampleList) {
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
        TripB currentItem = mExampleList.get(position);
        holder.from.setText(currentItem.getFfrom());
        holder.to.setText(currentItem.getTto());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
        holder.name.setText(currentItem.getCreator().getName());
        holder.number_of_passengers.setText(currentItem.getSeatesStatus()+"");
        holder.number_of_bags.setText(currentItem.getbagsStatus()+"");
        holder.price.setText(currentItem.getPrice()+" €");
        holder.rate.setText(currentItem.getCreator().getRate()+"");
        if(!currentItem.getCreator().getPicture().equals("null")){
            holder.img.setImageBitmap(StringToBitMap(currentItem.getCreator().getPicture()));
        }

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