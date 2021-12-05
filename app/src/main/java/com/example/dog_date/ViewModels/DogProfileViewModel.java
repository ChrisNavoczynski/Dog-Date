package com.example.dog_date.ViewModels;

import com.example.dog_date.models.DogProfileModel;
import com.example.dog_date.Upload;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.function.Consumer;

public class DogProfileViewModel {
    private DogProfileModel model;

    public DogProfileViewModel() { model = new DogProfileModel(); }

    public void getProfileInfo(Consumer<Upload> responseCallback) {
        model.getProfileInformation(
                (DocumentSnapshot queryDocumentSnapshot) -> {
                    if (queryDocumentSnapshot != null) {
                        Upload profileInfo = queryDocumentSnapshot.toObject(Upload.class);
                        responseCallback.accept(profileInfo);
                    }
                },
                (databaseError -> System.out.println("Error reading profile information: " + databaseError))
        );
    }

    public void updateProfile(Upload profile) { model.updateProfileById(profile); }

    public void clear() { model.clear(); }
}
