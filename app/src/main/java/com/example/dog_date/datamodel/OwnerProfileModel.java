package com.example.dog_date.datamodel;

import com.example.dog_date.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OwnerProfileModel {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fb;
    private List<ListenerRegistration> listeners;
    private String userID;

    public OwnerProfileModel() {
        fb = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
    }

    public void getProfileInformation(Consumer<DocumentSnapshot> dataChangedCallBack, Consumer<FirebaseFirestoreException> dataErrorCallback) {
        ListenerRegistration listener = fb.collection("Users").document(userID)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        dataErrorCallback.accept(e);
                    }
                    dataChangedCallBack.accept(queryDocumentSnapshots);
                });
        listeners.add(listener);
    }

    public void updateProfileById(Upload profile) {
        DocumentReference profileRef = fb.collection("Users").document(profile.getUserID());
        Map<String, Object> data = new HashMap<>();
        data.put("dogAge", profile.getDogAge());
        data.put("dogBio", profile.getDogBio());
        data.put("dogBreed", profile.getDogBreed());
        data.put("dogName", profile.getDogName());
        profileRef.update(data);
    }

    public void clear() {
        listeners.forEach(ListenerRegistration::remove);
    }
}
