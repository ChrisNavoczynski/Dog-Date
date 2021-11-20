package com.example.dog_date;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.Match.MatchActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwipeActivity extends AppCompatActivity {

    private Upload users_data[];
    private arrayAdapter arrayAdapter;
    private int i;
    DrawerLayout drawerLayout;
    boolean nope = false;

    private FirebaseAuth mAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    String ownername, ownerage, ownergender, ownerStates, currentUser, currentUserState;
    ListView listView;
    List<Upload> rowItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_activity);


        drawerLayout = findViewById(R.id.drawer_layout);
        rowItem = new ArrayList<Upload>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        getMatch();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItem );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);



        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItem.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Upload user = (Upload) dataObject;
                String userID = user.getUserID();

                Map<String, Object> disLikeIt = new HashMap<>();
                disLikeIt.put("userID", userID);

                DocumentReference userDb = db.collection("Users").document(currentUser);
                userDb.collection("Nope")
                        .add(disLikeIt)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SwipeActivity.this, "Nope",Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Upload user = (Upload) dataObject;
                String userID = user.getUserID();

                Map<String, Object> likeIt = new HashMap<>();
                likeIt.put("userID", userID);

                DocumentReference userDb = db.collection("Users").document(currentUser);
                userDb.collection("Yeah")
                        .add(likeIt)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SwipeActivity.this, "you like this person",Toast.LENGTH_SHORT).show();
                            }
                        });
                isMatch(userID);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(SwipeActivity.this, "Click",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getMatch() {

        DocumentReference currentUserDB = db.collection("Users").document(currentUser);
        currentUserDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ownerStates = documentSnapshot.getString("ownerStates");

                CollectionReference  collectionReference = db.collection("Users");

                Query userQuery = collectionReference.whereEqualTo("ownerStates", ownerStates);

                userQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Upload user = documentSnapshot.toObject(Upload.class);

                            String userID = user.getUserID();

                            Log.i(TAG, "nope in here what is that now " + nope);

                            if (!userID.equals(currentUser) && !checkNope(userID)) {
                                rowItem.add(user);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }

    private void isMatch(String userID){
        CollectionReference  collectionReference = db.collection("Users").document(userID).collection("Yeah");
        Query likeQuery = collectionReference.whereEqualTo("userID", currentUser);

        likeQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String uID = documentSnapshot.getId();
                    addToMatch(uID, userID);
                    Log.i(TAG, "New Match!!!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "no one likes you man T.T");
            }
        });
    }

    private void addToMatch(String key, String userID){
        Map<String, Object> chatID = new HashMap<>();
        chatID.put("chatWithUser", userID);
        chatID.put("key", key);
        DocumentReference currentUserDB = db.collection("Users").document(currentUser);
        currentUserDB.collection("Chat")
                .add(chatID)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "add to chat");
                    }
                });
        Map<String, Object> chat2ID = new HashMap<>();
        DocumentReference userDB = db.collection("Users").document(userID);
        chat2ID.put("chatWithUser", currentUser);
        chat2ID.put("key", key);
        userDB.collection("Chat")
                .add(chat2ID)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "add to chat 2");
                    }
                });
    }

    private boolean checkNope(String userID){

        CollectionReference currentUserDb = db.collection("Users").document(currentUser).collection("Nope");
        Query disLikeQuery = currentUserDb.whereEqualTo("userID", userID);

        disLikeQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                nope = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "not in here");
            }
        });

        CollectionReference currentUser2Db = db.collection("Users").document(currentUser).collection("Chat");
        Query likeQuery = currentUserDb.whereEqualTo("userID", userID);

        likeQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                nope = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "not in here");
            }
        });

        Log.i(TAG, "nope is here" + nope);

        return nope;
    }



    public void goToMatch(View view) {
        Intent intent = new Intent(SwipeActivity.this, MatchActivity.class);
        startActivity(intent);
        return;
    }


    public void ClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickDogProfile (View view) {
        MainActivity.redirectActivity(this, DogProfilePage.class);
    }

    public void ClickOwnerProfile (View view) {
        MainActivity.redirectActivity(this,MatchActivity.class);
    }

    public void ClickChatMessaging (View view) {
        MainActivity.redirectActivity(this, CurrentUserActivity.class);
    }

    public void ClickLogout (View view) {
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}