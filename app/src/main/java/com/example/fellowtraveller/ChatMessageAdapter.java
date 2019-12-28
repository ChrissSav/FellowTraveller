package com.example.fellowtraveller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

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
    private DatabaseReference messagesDatabase;

    public ChatMessageAdapter(List<ChatMessages> aMessageList, Context aContext){
        this.mMessageList = aMessageList;
        this.context = aContext;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        for(i=0;i<mMessageList.size();i++){

            if(true){

            }
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_received, parent, false);

        return new MessageViewHolder(v);


    }



    //@SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MessageViewHolder viewHolder, int position) {
        //currentId = getId();

        ChatMessages c = mMessageList.get(position);
        String from_user = c.getFrom();

//        if(from_user.equals(currentId)){
//            viewHolder.messageText.setBackgroundColor(Color.WHITE);
//            viewHolder.messageText.setTextColor(R.color.grey);
//
//        }else{
//            viewHolder.messageText.setBackgroundColor(Color.BLACK);
//            viewHolder.messageText.setTextColor(R.color.grey);
//
//        }
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
