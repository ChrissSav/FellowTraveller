package com.example.fellowtraveller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {
    private List<ChatMessages> mMessageList;
    private String currentId;
    private String id;
    private int i;
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Context context;
    private String userId;
    private DatabaseReference messagesDatabase, profileDatabase;
    private static final int MSG_TYPE_RIGHT = 0;
    private static final int MSG_TYPE_LEFT = 1;
    private static final int MSG_TYPE_LEFT_NO_IMG = 2;
    private String ImageUrl;


    public ChatMessageAdapter(List<ChatMessages> aMessageList, Context aContext) {
        this.mMessageList = aMessageList;
        this.context = aContext;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        userId = getId();


        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_send, parent, false);
            return new ChatMessageAdapter.MessageViewHolder(view);
        } else if (viewType == MSG_TYPE_LEFT_NO_IMG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_received_no_picture, parent, false);
            return new ChatMessageAdapter.MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_received, parent, false);
            return new ChatMessageAdapter.MessageViewHolder(view);

        }
    }


    //@SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int position) {
        //currentId = getId();

        ChatMessages c = mMessageList.get(position);
        String from_user = c.getFrom();
        profileDatabase = FirebaseDatabase.getInstance().getReference();
        //Load Image
        if(from_user!=null) {
            profileDatabase.child("Users").child(from_user).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String imageUrl = dataSnapshot.child("image").getValue(String.class);

                    Picasso.get().load(imageUrl).placeholder(R.drawable.cylinder).into(viewHolder.profileImage);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        //Set Message
        viewHolder.messageText.setText(c.getMessage());
        //Seen/Send Message
        if(position == mMessageList.size()-1){
            if (c.isSeen()!=null && c.isSeen()) {
                    viewHolder.seenTextView.setText("Διαβάστηκε");
                } else {
                    viewHolder.seenTextView.setText("Στάλθηκε");
                }

        }else{
            viewHolder.seenTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public CircleImageView profileImage;
        public TextView seenTextView;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_shape);
            profileImage = (CircleImageView) view.findViewById(R.id.message_pic);
            seenTextView = (TextView) view.findViewById(R.id.message_seen);

        }


    }
    //Αν θα γυρισει layout ως sender ή receiver

    @Override
    public int getItemViewType(int position) {
        id = getId();

        if (mMessageList.get(position).getFrom()!= null && id!=null && mMessageList.get(position).getFrom().equals(id)) {
            return MSG_TYPE_RIGHT;
        }
        if( position + 1 < mMessageList.size()) {
            if ((mMessageList.get(position).getFrom()!=null && id!=null && mMessageList.get(position + 1).getFrom()!=null && !mMessageList.get(position).getFrom().equals(id)) && (!mMessageList.get(position + 1).getFrom().equals(id))) {
                return MSG_TYPE_LEFT_NO_IMG;
            }
        }
        return MSG_TYPE_LEFT;
    }








    public String getId () {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            String id = "-1";

            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i == 1) {
                    id = text;
                    return id;

                }
                i++;
            }
            //String t = "name : "+name.getText()+"\n"+"email: "+email.getText()+"\n"+"id : "+id;
            //Toast.makeText(MainHomeActivity.this,t,Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }


}

