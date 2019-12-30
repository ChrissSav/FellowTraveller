package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatConversationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<ChatConversationItem> conversations = new ArrayList<>();
        conversations.add(new ChatConversationItem("default","Γιώργος Ανδρεου", true, true, 542346363, "131", "132"));
        conversations.add(new ChatConversationItem("default","Σεμπάστιαν Ανδρεου", false, true, 545345363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Τόλης Μόλης", true, false, 542345563,"131", "132"));
        conversations.add(new ChatConversationItem("default","Γιώργος Γεωργίου", false, false, 542545363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Μάκης Ανδρεου", true, true, 542345333,"131", "132"));
        conversations.add(new ChatConversationItem("default","Γιώργος Ανδρεου", true, true, 542346363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Σεμπάστιαν Ανδρεου", false, true, 545345363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Τόλης Μόλης", true, false, 542345563,"131", "132"));
        conversations.add(new ChatConversationItem("default","Γιώργος Γεωργίου", false, false, 542545363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Μάκης Ανδρεου", true, true, 542345333,"131", "132"));
        conversations.add(new ChatConversationItem("default","Γιώργος Ανδρεου", true, true, 542346363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Σεμπάστιαν Ανδρεου", false, true, 545345363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Τόλης Μόλης", true, false, 542345563,"131", "132"));
        conversations.add(new ChatConversationItem("default","Γιώργος Γεωργίου", false, false, 542545363,"131", "132"));
        conversations.add(new ChatConversationItem("default","Μάκης Ανδρεου", true, true, 542345333,"131", "132"));


        recyclerView = findViewById(R.id.chat_convs_recycler_view);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ChatConversationAdapter(conversations);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ChatConversationAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent a = new Intent(Chat.this, ChatConversation.class);
                startActivity(a);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();



    }
}
