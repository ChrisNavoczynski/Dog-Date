package com.example.dog_date.chat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_date.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EditText messageBox;
    private Button sendButton;


    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private String currentUser, userName, currentUserState, chatId, key;

    CollectionReference mDatabaseChat;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        currentUser = "they";
        currentUserState = "Oregon";
        userName = "Dave";
        key = "590c73e9-765d-4527-b0ed-6b50860439e7";

        mDatabaseChat = FirebaseFirestore.getInstance().collection("Chat");
        //userName = getIntent().getExtras().getString("FriendId");
        //userState = getIntent().getExtras().getString("FriendState");

        getChatID();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(MessageActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new ChatAdapter(getDataSetChat(), MessageActivity.this);
        mRecyclerView.setAdapter(mChatAdapter);

        messageBox = findViewById(R.id.message);
        sendButton = findViewById(R.id.send);

        sendButton.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        String sendMessageText = messageBox.getText().toString();

        if(!sendMessageText.isEmpty()){
            DocumentReference newMessageDb = mDatabaseChat.document(key);

            HashMap newMessage = new HashMap();
            newMessage.put("createdByUser", currentUser);
            newMessage.put("text", sendMessageText);
            newMessageDb.collection("communication").add(newMessage);
        }
        messageBox.setText(null);
    }

    private void getChatID() {
    }

    private List<ChatObject> getDataSetChat() {
        return resultsChat;
    }
}
