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

import com.squareup.picasso.Picasso;

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

        viewModel = new DogProfileViewModel();
        profilePictureImageView = findViewById(R.id.profile_picture);
        dogNameAgeTextView = findViewById(R.id.dog_name_and_age_profile);
        dogBreedTextView = findViewById(R.id.dog_breed_profile);
        dogBioTextView = findViewById(R.id.dog_bio_profile);
        drawerLayout = findViewById(R.id.drawer_layout);

        viewModel.getProfileInfo(
                (Upload profileInfo) -> {
                    dogAge = profileInfo.getDogAge();
                    dogBio = profileInfo.getDogBio();
                    dogBreed = profileInfo.getDogBreed();
                    dogName = profileInfo.getDogName();
                    imageUri = profileInfo.getmImageUrl();

                    String dogNameAndAge = dogName + ", " + dogAge;

                    Picasso.get().load(imageUri).resize(750, 750).centerCrop().into(profilePictureImageView);
                    dogNameAgeTextView.setText(dogNameAndAge);
                    dogBreedTextView.setText(dogBreed);
                    dogBioTextView.setText(dogBio);
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

    public void ClickDogProfile (View view) { recreate(); }

    public void ClickOwnerProfile (View view) { MainActivity.redirectActivity(this,OwnerProfilePage.class); }

    public void ClickLogout (View view) {
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}
