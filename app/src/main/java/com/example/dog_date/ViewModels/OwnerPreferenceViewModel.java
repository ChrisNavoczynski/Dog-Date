package com.example.dog_date.ViewModels;

import com.example.dog_date.models.OwnerPreferenceItems;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class OwnerPreferenceViewModel {

    private com.example.dog_date.models.FirebasePreferenceModel preferenceModel;

    public OwnerPreferenceViewModel(){
        preferenceModel = new com.example.dog_date.models.FirebasePreferenceModel();
    }

    public void addOwnerPreferences(OwnerPreferenceItems o){
        preferenceModel.addOwnerPreferences(o);
    }

    public void getOwnerPreferences(Consumer<ArrayList<OwnerPreferenceItems>> responseCallback) {
        preferenceModel.getPreferences(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<OwnerPreferenceItems> todoItems = new ArrayList<>();
                        for (DocumentSnapshot todoSnapshot : querySnapshot.getDocuments()) {
                            OwnerPreferenceItems item = todoSnapshot.toObject(OwnerPreferenceItems.class);
                            assert item != null;
                            item.UserID = todoSnapshot.getId();
                            todoItems.add(item);
                        }
                        responseCallback.accept(todoItems);
                    }
                },
                (databaseError -> System.out.println("Error reading Owner Preferences: " + databaseError))
        );
    }

    public void updateOwnerPreferencesByID(OwnerPreferenceItems o) {
        preferenceModel.updateOwnerPreferencesByID(o);
    }

    public void clear() {
        preferenceModel.clear();
    }
}

