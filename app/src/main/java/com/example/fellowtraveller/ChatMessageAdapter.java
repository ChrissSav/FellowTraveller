package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {
    private List<ChatMessages> mMessageList;

    public ChatMessageAdapter(List<ChatMessages> aMessageList){
        this.mMessageList = aMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_received, parent, false);

        return new MessageViewHolder(v);


    }



    @Override
    public void onBindViewHolder(MessageViewHolder viewHolder, int position) {
        ChatMessages c = mMessageList.get(position);
        viewHolder.messageText.setText(c.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public CircleImageView profileImage;

        public MessageViewHolder(View view){
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_shape);
            profileImage = (CircleImageView) view.findViewById(R.id.message_pic);

        }



    }
}
