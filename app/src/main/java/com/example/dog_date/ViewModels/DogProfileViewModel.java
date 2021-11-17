package com.example.dog_date.ViewModels;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.dog_date.DataModels.DogProfileModel;
import com.example.dog_date.Models.OnGetDataListener;
import com.example.dog_date.Upload;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DogProfileViewModel {
    private DogProfileModel model;

    public DogProfileViewModel() { model = new DogProfileModel(); }

    public void getProfileInfo(Consumer<Map<String, String>> responseCallback) {
        model.getProfileInformation(
                (DocumentSnapshot queryDocumentSnapshot) -> {
                    if (queryDocumentSnapshot != null) {
                        Map<String, String> profileInfo = new HashMap<>();
                        profileInfo.put("dogAge", queryDocumentSnapshot.getString("dogAge"));
                        profileInfo.put("dogBio", queryDocumentSnapshot.getString("dogBio"));
                        profileInfo.put("dogBreed", queryDocumentSnapshot.getString("dogBreed"));
                        profileInfo.put("dogName", queryDocumentSnapshot.getString("dogName"));
                        responseCallback.accept(profileInfo);
                        if(profileInfo.get("dogAge") != null) {
                            Log.i("Profile DogAge:", profileInfo.get("dogAge"));
                        } else {
                            Log.i("Profile DogAge:", null);
                        }
                    }
                },
                (databaseError -> System.out.println("Error reading profile information: " + databaseError))
        );



//        model.getProfileInformation(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    System.out.println("Error reading profile information" + error);
//                    activityCallBack.onFailure();
//                    return;
//                }
//
//                if (queryDocumentSnapshots != null) {
//                    Map<String, Object> data = queryDocumentSnapshots.getData();
//                    if (data != null) {
//                        Object dogAge = data.get("dogAge");
//                    }
//                }
//            }
//        });
    }

    public void updateProfile(Upload profile) { model.updateProfileById(profile); }

    public void clear() { model.clear(); }
}
