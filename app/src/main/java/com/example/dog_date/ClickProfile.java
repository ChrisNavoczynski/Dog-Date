package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.DogProfilePage;
import com.example.dog_date.MainActivity;
import com.example.dog_date.R;
import com.example.dog_date.SwipeActivity;
import com.example.dog_date.Upload;
import com.example.dog_date.viewmodels.OwnerProfileViewModel;
import com.squareup.picasso.Picasso;

public class ClickProfile extends AppCompatActivity {
    ImageView profilePictureImageView;
    TextView ownerNameAgeTextView;
    TextView ownerGenderTextView;
    TextView ownerStatesTextView;
    TextView distanceTextView;

    DrawerLayout drawerLayout;

    String ownerName;
    String ownerGender;
    String ownerAge;
    String ownerStates;
    String ownerImage;
    String distance;
    double userLat, userLong, currentUserLat, currentUserlong;

    private OwnerProfileViewModel viewModel;

    private String imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_owner);

        viewModel = new OwnerProfileViewModel();
        profilePictureImageView = findViewById(R.id.profile_picture);
        ownerNameAgeTextView = findViewById(R.id.owner_name_and_age_profile);
        ownerGenderTextView = findViewById(R.id.owner_gender_profile);
        ownerStatesTextView = findViewById(R.id.owner_states_profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        distanceTextView = findViewById(R.id.distance);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b.containsKey("ownerName") && b.containsKey("ownerAge") && b.containsKey("ownerGender") && b.containsKey("ownerStates") && b.containsKey("ownerImage") && b.containsKey("ownerLat") && b.containsKey("ownerLong")) {
            ownerName = b.getString("ownerName");
            ownerAge = b.getString("ownerAge");
            ownerGender = b.getString("ownerGender");
            ownerStates = b.getString("ownerStates");
            ownerImage = b.getString("ownerImage");
            userLat = b.getDouble("ownerLat");
            userLong = b.getDouble("ownerLong");
        }

        viewModel.getProfileInfo(
                (Upload profileInfo) -> {
                    currentUserLat = profileInfo.getLatitude();
                    currentUserlong = profileInfo.getLongitude();

                    distance = String.valueOf(getDistance(userLat,userLong, currentUserLat, currentUserlong));

                    String ownerNameAndAge = ownerName + ", " + ownerAge;
                    String distanceInMiles = distance + "miles";

                    Picasso.get().load(ownerImage).resize(300 , 320).centerCrop().into(profilePictureImageView);
                    ownerNameAgeTextView.setText(ownerNameAndAge);
                    ownerGenderTextView.setText(ownerGender);
                    ownerStatesTextView.setText(ownerStates);
                    distanceTextView.setText(distanceInMiles);
                }
        );
    }

    public Double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
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

    public void ClickDogProfile (View view) { MainActivity.redirectActivity(this, DogProfilePage.class); }

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