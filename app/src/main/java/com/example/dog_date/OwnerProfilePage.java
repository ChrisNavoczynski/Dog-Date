package com.example.dog_date;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.ViewModels.OwnerProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class OwnerProfilePage extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    ImageView profilePictureImageView;
    TextView ownerNameAgeTextView;
    TextView ownerGenderTextView;
    TextView ownerStatesTextView;
    TextView ownerBioTextView;
    EditText MaxRange;
    Button updateRange;

    DrawerLayout drawerLayout;

    String ownerName;
    String ownerGender;
    String ownerAge;
    String ownerStates;
    String ownerBio;
    Double maxRange;
    String maxRangeS;

    private OwnerProfileViewModel viewModel;

    private String imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_profile_page);

        viewModel = new OwnerProfileViewModel();
        profilePictureImageView = findViewById(R.id.profile_picture);
        ownerNameAgeTextView = findViewById(R.id.owner_name_and_age_profile);
        ownerGenderTextView = findViewById(R.id.owner_gender_profile);
        ownerStatesTextView = findViewById(R.id.owner_states_profile);
        ownerBioTextView = findViewById(R.id.owner_bio_profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        MaxRange = findViewById(R.id.ownerSetRange);
        mAuth = FirebaseAuth.getInstance();
        updateRange = findViewById(R.id.updateRange);
        db = FirebaseFirestore.getInstance();

        viewModel.getProfileInfo(
                (Upload profileInfo) -> {
                    ownerAge = profileInfo.getOwnerAge();
                    ownerStates = profileInfo.getOwnerStates();
                    ownerGender = profileInfo.getOwnerGender();
                    ownerName = profileInfo.getOwnerName();
                    ownerBio = profileInfo.getOwnerBio();
                    imageUri = profileInfo.getmImageUrl();
                    maxRange = profileInfo.getMaxRange();

                    String ownerNameAndAge = ownerName + ", " + ownerAge;

                    Picasso.get().load(imageUri).resize(750, 750).centerCrop().into(profilePictureImageView);
                    ownerNameAgeTextView.setText(ownerNameAndAge);
                    ownerGenderTextView.setText(ownerGender);
                    ownerStatesTextView.setText(ownerStates);
                    ownerBioTextView.setText(ownerBio);
                    MaxRange.setText(maxRange.toString());
                }
        );
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

    public void ClickDogProfile (View view) { MainActivity.redirectActivity(this,DogProfilePage.class); }

    public void ClickOwnerProfile (View view) { recreate(); }

    public void ClickLogout (View view) {
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

    public void updataMaxRange(View view){

        DocumentReference profileRef = db.collection("Users").document(mAuth.getCurrentUser().getUid());
        Map<String, Object> data = new HashMap<>();
        maxRangeS = MaxRange.getText().toString();
        data.put("maxRange", Double.valueOf(maxRangeS));
        profileRef.update(data);
        /*viewModel.updateRange(
                (Upload profileInfo) -> {

                }
        );*/
    }
}
