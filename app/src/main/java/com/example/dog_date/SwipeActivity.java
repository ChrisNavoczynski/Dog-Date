package com.example.dog_date;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SwipeActivity extends AppCompatActivity {

    private Upload users_data[];
    private arrayAdapter arrayAdapter;
    private int i;
    DrawerLayout drawerLayout;

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
        currentUser = "they";
        currentUserState = "Oregon";

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

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Upload user = (Upload) dataObject;
                String ownername = user.getOwnerName();
                String ownerStates = user.getOwnerStates();
                String uniqueID = UUID.randomUUID().toString();
                Map<String, Object> chatID = new HashMap<>();
                chatID.put("chatID", uniqueID);
                DocumentReference userDb = db.collection("Profiles").document("location").collection(ownerStates.trim()).document(ownername.trim());
                userDb.collection("Friends").document(currentUser)
                        .set(chatID)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                isFriend(ownername, uniqueID);
                                Toast.makeText(SwipeActivity.this, "add friends",Toast.LENGTH_SHORT).show();
                            }
                        });
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
        DocumentReference docRef = db.collection("Profiles").document("location");
        docRef.collection("Oregon").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Upload user = documentSnapshot.toObject(Upload.class);

                            rowItem.add(user);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SwipeActivity.this, "error",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void isFriend(String ownername, String uniqueID){
        Map<String, Object> chatID = new HashMap<>();
        chatID.put("chatID", uniqueID);
        DocumentReference currentUserDb = db.collection("Profiles").document("location").collection(currentUserState.trim()).document(currentUser.trim());
        currentUserDb.collection("Friend").document(ownername)
                .set(chatID)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
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
        MainActivity.redirectActivity(this,OwnerProfile.class);
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