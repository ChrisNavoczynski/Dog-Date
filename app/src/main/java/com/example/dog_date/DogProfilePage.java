package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_profile_page);

//        Intent intent = getIntent();
//        Bundle b = intent.getExtras();
//
//        if (b.containsKey("dogName") && b.containsKey("dogBreed") && b.containsKey("dogAge") && b.containsKey("dogBio")) {
//            dogName = b.getString("dogName");
//            dogBreed = b.getString("dogBreed");
//            dogAge = b.getString("dogAge");
//            dogBio = b.getString("dogBio");
//        }



        profilePictureImageView = findViewById(R.id.profile_picture);
        dogNameAgeTextView = findViewById(R.id.dog_name_and_age_profile);
        dogBreedTextView = findViewById(R.id.dog_breed_profile);
        dogBioTextView = findViewById(R.id.dog_bio_profile);

        String dogNameAndAge = dogName + ", " + dogAge;

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
