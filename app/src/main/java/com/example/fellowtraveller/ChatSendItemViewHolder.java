package com.example.fellowtraveller;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatSendItemViewHolder extends RecyclerView.ViewHolder {
    private TextView message;

    public ChatSendItemViewHolder(View v) {
        super(v);
        message = (TextView) v.findViewById(R.id.message_shape);

    }




    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }


}
