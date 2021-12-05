package com.example.dog_date.models;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FirebasePreferenceModel {

    private FirebaseFirestore db;
    private List<ListenerRegistration> listeners;

    public FirebasePreferenceModel(){
        db = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
    }


    public void addOwnerPreferences(OwnerPreferenceItems o){
        CollectionReference preferenceItemRef = db.collection("Preferences");
        preferenceItemRef.add(o);
    }

    public void getPreferences(Consumer<QuerySnapshot> dataChangedCallback, Consumer<FirebaseFirestoreException> dataErrorCallback) {
        ListenerRegistration listener = db.collection("Preferences")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        dataErrorCallback.accept(e);
                    }

                    dataChangedCallback.accept(queryDocumentSnapshots);
                });
        listeners.add(listener);
    }

    public void getOwnerPreferences(Consumer<QuerySnapshot> dataChangedCallback, Consumer<FirebaseFirestoreException> dataErrorCallback) {
        ListenerRegistration listener = db.collection("Preferences")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        dataErrorCallback.accept(e);
                    }

                    dataChangedCallback.accept(queryDocumentSnapshots);
                });
        listeners.add(listener);
    }


    public void updateOwnerPreferencesByID(OwnerPreferenceItems o) {
        DocumentReference preferenceItemRef = db.collection("Preferences").document(o.UserID);
        Map<String, Object> data = new HashMap<>();
        data.put("ownerGenderP", o.ownerGenderP);
        data.put("ownerMaxAge", o.ownerMaxAge);
        data.put("ownerMinAge", o.ownerMinAge);
        data.put("dogGenderP", o.genderDogP);
        data.put("dogSizeP", o.dogSizeP);
        data.put("dogBreedP", o.breedP);
        data.put("dogMaxAgeP", o.dogMaxAgeP);
        data.put("dogMinAgeP", o.dogMinAgep);
    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(ListenerRegistration::remove);
    }


}
