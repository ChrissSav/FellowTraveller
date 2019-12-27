package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatConversation extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Toolbar convToolabar;
    private DatabaseReference chatDatabase;
    private String id, userId;
    private ImageButton sendImageButton;
    private String textWritted;
    private EditText chatEditText;
    private String chatUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        textWritted="  ";
        chatUser = "127";
        userId = getId();

        chatDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(userId).child(chatUser).child("Messages").child("Message " + random());;

        sendImageButton = findViewById(R.id.chat_send);
        chatEditText = findViewById(R.id.ask_textView);

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textWritted = chatEditText.getText().toString();

                HashMap<String,String> newChatter = new HashMap<>();
                newChatter.put("seen", "false");
                newChatter.put("text", textWritted);
                newChatter.put("sender", userId);
                newChatter.put("receiver", "128");

                chatDatabase.setValue(newChatter);


            }
        });










    }
    public String getId(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            String id ="-1";

            int i = 0;
            while ((text = br.readLine()) != null) {
                if (i==1){
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
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

}
