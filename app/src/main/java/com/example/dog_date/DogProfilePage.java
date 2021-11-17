package com.example.dog_date;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.ViewModels.DogProfileViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DogProfilePage extends AppCompatActivity {
    ImageView profilePictureImageView;
    TextView dogNameAgeTextView;
    TextView dogBreedTextView;
    TextView dogBioTextView;

    DrawerLayout drawerLayout;

    String dogName;
    String dogBreed;
    String dogAge;
    String dogBio;

    private DogProfileViewModel viewModel;

    private String imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_profile_page);

//        final FirebaseFirestore database = FirebaseFirestore.getInstance();
//        DocumentReference docRef = database.collection("Profiles/location/Oregon").document("TestNov8");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    Upload p = documentSnapshot.toObject(Upload.class);
//                    imageUri = p.getmImageUrl();
//                    dogName = p.getDogName();
//                    dogBreed = p.getDogBreed();
//                    dogAge = p.getDogAge();
//                    dogBio = p.getDogBio();
//                }
//            }
//        });

//        final FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection("Profiles/location/Oregon")
//                .whereEqualTo("TestNov8", true)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if (!queryDocumentSnapshots.isEmpty()) {
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//                                Upload p = d.toObject(Upload.class);
//                                imageUri = p.getmImageUrl();
//                                dogName = p.getDogName();
//                                dogBreed = p.getDogBreed();
//                                dogAge = p.getDogAge();
//                                dogBio = p.getDogBio();
//                            }
//                        } else {
//                            Toast.makeText(DogProfilePage.this, "No data is found in the database.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(DogProfilePage.this, "Failed to get the data.", Toast.LENGTH_SHORT).show();
//            }
//        });

//        Intent intent = getIntent();
//        Bundle b = intent.getExtras();
//
//        if (b.containsKey("dogName") && b.containsKey("dogBreed") && b.containsKey("dogAge") && b.containsKey("dogBio")) {
//            dogName = b.getString("dogName");
//            dogBreed = b.getString("dogBreed");
//            dogAge = b.getString("dogAge");
//            dogBio = b.getString("dogBio");
//        }

        viewModel = new DogProfileViewModel();

        viewModel.getProfileInfo(
                (Map<String, String> profileInfo) -> {
                    dogAge = profileInfo.get("dogAge");
                    if(dogAge != null) {
                        Log.i("dogAge", dogAge);
                    } else {
                        Log.i("dogAge", "null");
                    }
                    dogBio = profileInfo.get("dogBio");
                    dogBreed = profileInfo.get("dogBreed");
                    dogName = profileInfo.get("dogName");
                }
        );

        profilePictureImageView = findViewById(R.id.profile_picture);
        dogNameAgeTextView = findViewById(R.id.dog_name_and_age_profile);
        dogBreedTextView = findViewById(R.id.dog_breed_profile);
        dogBioTextView = findViewById(R.id.dog_bio_profile);

        String dogNameAndAge = dogName + ", " + dogAge;

//        profilePictureImageView.setImageURI(Uri.parse(imageUri));
        profilePictureImageView.setImageResource(R.drawable.default_pfp);
        dogNameAgeTextView.setText(dogNameAndAge);
        dogBreedTextView.setText(dogBreed);
        dogBioTextView.setText(dogBio);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        MainActivity.redirectActivity(this, SwipeActivity.class);
    }

    public void ClickDogProfile (View view) { recreate(); }

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
