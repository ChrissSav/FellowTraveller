package com.example.fellowtraveller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatConversationAdapter extends RecyclerView.Adapter<ChatConversationAdapter.ConversationViewHolder> {
    private ArrayList<ChatConversationItem> convList;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;

    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView conversationImage;
        private TextView nameTV;
        private TextView lastMessageTV;
        private TextView onlineStatusTV;

        public ConversationViewHolder(View itemView, final onItemClickListener listener){
            super(itemView);

            conversationImage = itemView.findViewById(R.id.conv_profile_pic);
            nameTV = itemView.findViewById(R.id.conv_user_name);
            lastMessageTV = itemView.findViewById(R.id.conv_new_message);
            onlineStatusTV = itemView.findViewById(R.id.conv_online_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }

    public ChatConversationAdapter(ArrayList<ChatConversationItem> aConvList){
        this.convList = aConvList;
    }
    @Override
    public ConversationViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent,false);
        ConversationViewHolder cvh = new ConversationViewHolder(v, mListener);
        return  cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        ChatConversationItem currItem = convList.get(position);

        Picasso.get().load(convList.get(position).getImageUrl()).into(holder.conversationImage);
        holder.nameTV.setText(currItem.getName());
        if(!currItem.isNewMessage()){
            holder.lastMessageTV.setVisibility(View.GONE);
        }
        if (!currItem.isOnlineStatus()){
            holder.onlineStatusTV.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return convList.size();
    }
}
