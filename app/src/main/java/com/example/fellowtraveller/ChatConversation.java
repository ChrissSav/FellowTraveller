package com.example.fellowtraveller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.fellowtraveller.BetaActivity.NotificationActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatConversation extends AppCompatActivity {
    private static final String FILE_NAME = "fellow_login_state.txt";
    private Toolbar convToolabar;
    private DatabaseReference chatDatabase;
    private String id, userId;
    private ImageButton sendImageButton;
    private EditText chatEditText;
    private String chatUser;
    private RecyclerView mMessagesList;
    private SwipeRefreshLayout mRefreshLayout;
    private final List<ChatMessages> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private ChatMessageAdapter mAdapter;
    private DatabaseReference mMessageDatabase,userDatabase;
    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private String lastKey = "";
    private String prevKey = "";
    private Context context = ChatConversation.this;
    private MediaPlayer sfxButton;
    private TextView chatUserName, lastSeenTextView;
    private CircleImageView chatProfilePicture;
    private ImageButton backToConvButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        Intent intent= getIntent();
        chatUser = intent.getIntExtra("Creator_id",0)+""; //creator trip id
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("id");
            chatUser = j;
        }


        userId = getId(); //your id

        chatDatabase = FirebaseDatabase.getInstance().getReference();

        backToConvButton = findViewById(R.id.chat_back_to_conv);
        sendImageButton = findViewById(R.id.chat_send);
        chatEditText = findViewById(R.id.ask_textView);
        chatUserName = findViewById(R.id.chat_user_name);
        lastSeenTextView = findViewById(R.id.lastSeen);
        chatProfilePicture = findViewById(R.id.chat_conv_pic);
        sfxButton = MediaPlayer.create(this, R.raw.send_message_sfx);

        mAdapter = new ChatMessageAdapter(messagesList,context);
        mMessagesList = findViewById(R.id.messages_List);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe);

        mLinearLayout = new LinearLayoutManager(this);
        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);
        mMessagesList.setAdapter(mAdapter);

        chatDatabase.child("Users").child(userId).child("online").setValue("true");
        loadMessages();
        changeConversationStatus();

        backToConvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ChatConversation.this, Chat.class);
                startActivity(a);
                finish();
            }
        });


        //Create Chats.. one for you and the chatter.. and one for the chatter and you
        chatDatabase.child("Chat").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(chatUser)) {
                    Map myChatMap = new HashMap();
                    myChatMap.put("seen", false);
                    myChatMap.put("timestamp", ServerValue.TIMESTAMP);

                    //Pass the values to both chatters
                    Map storeMap = new HashMap();
                    storeMap.put("Chat/" + userId + "/" + chatUser, myChatMap);
                    storeMap.put("Chat/" + chatUser + "/" + userId, myChatMap);

                    chatDatabase.updateChildren(storeMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                //error
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Finished creating conversations
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();

            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage++; //increase the number of loading messages when we swipe
                itemPos = 0;
                loadMoreMessages();
            }
        });



    }

    private void changeConversationStatus() {
        userDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabase.child("Users").child(chatUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                chatUserName.setText(name);

                String imageUrl = dataSnapshot.child("image").getValue().toString();
                if (!imageUrl.equals("default")) {
                    Picasso.get().load(imageUrl).placeholder(R.drawable.cylinder).into(chatProfilePicture);
                }

                String online = dataSnapshot.child("online").getValue(String.class);
                Long time = (Long) dataSnapshot.child("lastSeen").getValue();
                if(online.equals("true")){
                    lastSeenTextView.setText("Ενεργός τώρα");
                }else{
                    ChatGetTimeAgo getTimeAgo = new ChatGetTimeAgo();
                    long lastTime = time;

                    String lastSeenTime  = getTimeAgo.getTimeAgo(lastTime, getApplicationContext());
                    lastSeenTextView.setText(lastSeenTime);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void loadMoreMessages() {
        DatabaseReference  messageRef = chatDatabase.child("Messages").child(userId).child(chatUser);
        Query messageQuery = messageRef.orderByKey().endAt(lastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessages message = dataSnapshot.getValue(ChatMessages.class);

                String messageKey = dataSnapshot.getKey();

                if(!prevKey.equals(messageKey)){

                    messagesList.add(itemPos++, message);

                }else{
                    prevKey = lastKey;
                }


                if(itemPos==1){

                    lastKey = messageKey;
                }


                mAdapter.notifyDataSetChanged();

                mRefreshLayout.setRefreshing(false);
                mLinearLayout.scrollToPositionWithOffset(10,0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void loadMessages() {

        DatabaseReference  messageRef = chatDatabase.child("Messages").child(userId).child(chatUser);
        Query messageQuery = messageRef.limitToLast(mCurrentPage*TOTAL_ITEMS_TO_LOAD);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessages message = dataSnapshot.getValue(ChatMessages.class);
                //The key at the top of the list its time, we storing his message key to continue loading from this key and above
                itemPos++;
                if(itemPos==1){
                    String messageKey = dataSnapshot.getKey();
                    lastKey = messageKey;
                    prevKey = messageKey;
                }
                messagesList.add(message);
                mAdapter.notifyDataSetChanged();
                mMessagesList.scrollToPosition(messagesList.size()-1);

                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        //chatDatabase.child("Users").child(userId).child("online").setValue("false");
        //chatDatabase.child("Users").child(userId).child("lastSeen").setValue(ServerValue.TIMESTAMP);
    }

    @Override
    public void onStart() {
        super.onStart();

        chatDatabase.child("Users").child(userId).child("online").setValue("true");
    }


    public String getId () {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
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

    public static String random () {
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

    public void sendMessage(){
        String getMessage = chatEditText.getText().toString();
        if(!TextUtils.isEmpty(getMessage)){

            sfxButton.start();

            String current_user_ref = "Messages/"+userId+"/"+chatUser;
            final String chat_user_ref = "Messages/"+chatUser+"/"+userId;

            DatabaseReference userMessagePush = chatDatabase.child("Messages").child(userId).child(chatUser).push();
            String push_id = userMessagePush.getKey();

            chatDatabase.child("Chat").child(userId).child(chatUser).child("sendMessage").setValue("true");
            chatDatabase.child("Chat").child(chatUser).child(userId).child("sendMessage").setValue("true");
            chatDatabase.child("Chat").child(userId).child(chatUser).child("lastMessage").setValue(ServerValue.TIMESTAMP);
            chatDatabase.child("Chat").child(chatUser).child(userId).child("lastMessage").setValue(ServerValue.TIMESTAMP);
            chatDatabase.child("Chat").child(userId).child(chatUser).child("chatterId").setValue(chatUser);
            chatDatabase.child("Chat").child(chatUser).child(userId).child("chatterId").setValue(userId);
            chatDatabase.child("Users").child(chatUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String name =  dataSnapshot.child("name").getValue(String.class);
                    chatDatabase.child("Chat").child(userId).child(chatUser).child("image").setValue(image);
                    chatDatabase.child("Chat").child(userId).child(chatUser).child("name").setValue(name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            chatDatabase.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String name =  dataSnapshot.child("name").getValue(String.class);
                    chatDatabase.child("Chat").child(chatUser).child(userId).child("image").setValue(image);
                    chatDatabase.child("Chat").child(chatUser).child(userId).child("name").setValue(name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Map messageMap = new HashMap();
            messageMap.put("message", getMessage);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", userId);


            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref+"/"+push_id,messageMap);
            messageUserMap.put(chat_user_ref+"/"+push_id,messageMap);
            //To delete the text when the user text someone
            chatEditText.setText("");

            chatDatabase.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if(databaseError != null){
                        //error
                    }
                }
            });
        }
    }


}