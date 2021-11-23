package com.example.dog_date;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.ViewModels.OwnerProfileViewModel;
import com.squareup.picasso.Picasso;

public class OwnerProfilePage extends AppCompatActivity {
    ImageView profilePictureImageView;
    TextView ownerNameAgeTextView;
    TextView ownerGenderTextView;
    TextView ownerStatesTextView;

    DrawerLayout drawerLayout;

    String ownerName;
    String ownerGender;
    String ownerAge;
    String ownerStates;

    private OwnerProfileViewModel viewModel;

    private String imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_profile_page);

        viewModel = new OwnerProfileViewModel();
        profilePictureImageView = findViewById(R.id.profile_picture);
        ownerNameAgeTextView = findViewById(R.id.dog_name_and_age_profile);
        ownerGenderTextView = findViewById(R.id.dog_breed_profile);
        ownerStatesTextView = findViewById(R.id.dog_bio_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        viewModel.getProfileInfo(
                (Upload profileInfo) -> {
                    ownerAge = profileInfo.getOwnerAge();
                    ownerStates = profileInfo.getOwnerStates();
                    ownerGender = profileInfo.getOwnerGender();
                    ownerName = profileInfo.getOwnerName();
                    imageUri = profileInfo.getmImageUrl();

                    String ownerNameAndAge = ownerName + ", " + ownerAge;

                    Picasso.get().load(imageUri).resize(750, 750).centerCrop().into(profilePictureImageView);
                    ownerNameAgeTextView.setText(ownerNameAndAge);
                    ownerGenderTextView.setText(ownerGender);
                    ownerStatesTextView.setText(ownerStates);
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
}
