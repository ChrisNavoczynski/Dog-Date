package com.example.dog_date.Match;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_date.R;
import com.example.dog_date.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;

    private String cusrrentUserID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManager = new LinearLayoutManager(MatchActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchAdapter = new MatchAdapter(getDataSetMatches(), MatchActivity.this);
        mRecyclerView.setAdapter(mMatchAdapter);


        getUserMatchId();

    }

    private void getUserMatchId(){
        CollectionReference collectionReference = db.collection("Users").document(cusrrentUserID).collection("Yeah");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                    String userId = documentSnapshot.getString("userID");
                    getuserData(userId);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "no one likes you man T.T");
            }
        });
    }

    private void getuserData(String userID){
        DocumentReference userDB = db.collection("Users").document(userID);
        userDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String tagerID = documentSnapshot.getString("UserID");
                String tagerName = documentSnapshot.getString("ownerName");
                String tagerPicture = documentSnapshot.getString("mImageUrl");

                MatchObject tager = new MatchObject(tagerID, tagerName, tagerPicture);

                resultsMatches.add(tager);
                mMatchAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "you are wrong! " + e);
            }
        });
    }

    private ArrayList<MatchObject> resultsMatches = new ArrayList<MatchObject>();
    private List<MatchObject> getDataSetMatches() {
        return resultsMatches;
    }


}