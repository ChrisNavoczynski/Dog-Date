package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {
    ImageView profilePictureImageView;
    TextView dogNameAgeTextView;
    TextView dogBreedTextView;
    String dogName;
    String dogBreed;
    String dogAge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b.containsKey("dogName") && b.containsKey("dogBreed") && b.containsKey("dogAge")) {
            dogName = b.getString("dogName");
            dogBreed = b.getString("dogBreed");
            dogAge = b.getString("dogAge");
        }

        profilePictureImageView = findViewById(R.id.profile_picture);
        dogNameAgeTextView = findViewById(R.id.dog_name_and_age_profile);
        dogBreedTextView = findViewById(R.id.dog_breed_profile);

        profilePictureImageView.setImageResource(R.drawable.default_pfp);
        dogNameAgeTextView.setText(dogName + ", " + dogAge);
        dogBreedTextView.setText(dogBreed);
    }
}
