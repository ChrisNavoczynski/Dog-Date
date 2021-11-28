package com.example.dog_date.viewmodels;

import com.example.dog_date.Models.PreferencesItems;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebasePreferenceViewModel {

    private com.example.dog_date.models.FirebasePreferenceModel preferenceModel;

    public FirebasePreferenceViewModel(){
        preferenceModel = new com.example.dog_date.models.FirebasePreferenceModel();
    }

    public void addPreferences(PreferencesItems item){
        preferenceModel.addPreferences(item);
    }

    public void getPreferences(Consumer<ArrayList<PreferencesItems>> responseCallback) {
        preferenceModel.getPreferences(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<PreferencesItems> todoItems = new ArrayList<>();
                        for (DocumentSnapshot todoSnapshot : querySnapshot.getDocuments()) {
                            PreferencesItems item = todoSnapshot.toObject(PreferencesItems.class);
                            assert item != null;
                            item.UserID = todoSnapshot.getId();
                            todoItems.add(item);
                        }
                        responseCallback.accept(todoItems);
                    }
                },
                (databaseError -> System.out.println("Error reading Preferences: " + databaseError))
        );
    }

    public void updatePreferencesByID(PreferencesItems item) {
        preferenceModel.updatePreferencesByID(item);
    }

    public void clear() {
        preferenceModel.clear();
    }
}
