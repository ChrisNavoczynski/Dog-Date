package com.example.dog_date;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.utilities.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class OwnerProfile extends AppCompatActivity {

    private Uri imageUri;

    ImageView uploadImage;

    EditText ownerName, ownerAge, ownerBioEditText;
    Button uploadButton,saveButton;

    RadioGroup radioGroup;
    RadioButton radioButton;
    DrawerLayout drawerLayout;

    String dogName;
    String dogBreed;
    String dogAge;
    String dogBio;


    double ownerLat, ownerLong;
    String ownername, ownerage, ownergender, ownerStates, ownerBio, userId;

    Spinner mySpinner;
    FusedLocationProviderClient fusedLocationProviderClient;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private StorageTask uploadTask;
    int radioId;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_profile);

        uploadImage = findViewById(R.id.imageToUpload);
        uploadButton = findViewById(R.id.uploadImage);
        saveButton = findViewById(R.id.saveButton);
        ownerAge = findViewById(R.id.ownerAge);
        radioGroup = findViewById(R.id.genderGroup);
        ownerName = findViewById(R.id.ownerName);
        ownerBioEditText = findViewById(R.id.owner_bio_edit_text);
        drawerLayout = findViewById(R.id.drawer_layout);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b.containsKey("dogName") && b.containsKey("dogBreed") && b.containsKey("dogAge") && b.containsKey("dogBio")) {
            dogName = b.getString("dogName");
            dogBreed = b.getString("dogBreed");
            dogAge = b.getString("dogAge");
            dogBio = b.getString("dogBio");
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                OwnerProfile.this
        );

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profile");


        // upload button is not for upload but is select picture from the phone
        uploadButton.setOnClickListener(v -> mGetContent.launch("image/*"));

        findViewById(R.id.locationBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(OwnerProfile.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(OwnerProfile.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else{
                    ActivityCompat.requestPermissions(OwnerProfile.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }
            }
        });

        // location button which ask for permission for location service and keeping running in the back.
        /*findViewById(R.id.locationBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            OwnerProfile.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    startLocationService();
                }
            }
        });

        findViewById(R.id.stopLocationBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopLocationService();
            }
        });*/

        /*
         save button gonna go to next activity and upload info to FireStore
        */
        saveButton.setOnClickListener(v -> uploadPic());

        // spinner for the states input
        mySpinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(OwnerProfile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if (location != null){
                        ownerLat = location.getLatitude();
                        ownerLong = location.getLongitude();
                    }else{
                        LocationRequest locationRequest = new LocationRequest()
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                ownerLat = locationResult.getLastLocation().getLatitude();
                                ownerLong = locationResult.getLastLocation().getLongitude();
                                Log.i(TAG, "here is the location " + ownerLat + ", " + ownerLong);
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                    }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    // save input if user rotate the phone
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.KEY_OWNER_NAME)) {
            ownerName.setText(savedInstanceState.getString(Constants.KEY_OWNER_NAME));
        }

        if (savedInstanceState.containsKey(Constants.KEY_OWNER_AGE)) {
            ownerAge.setText(savedInstanceState.getString(Constants.KEY_OWNER_AGE));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.KEY_OWNER_NAME, ownerName.getText().toString());
        outState.putString(Constants.KEY_OWNER_AGE, ownerAge.getText().toString());
    }

    // check owner name input is not empty
    public boolean validOwnerName() {
        ownername = ownerName.getEditableText().toString().trim();
        if (ownername.isEmpty()) {
            ownerName.setError("enter your user name pls");
            return false;
        } else if (ownername.length() > 15) {
            ownerName.setError("username too long");
            return false;
        } else {
            ownerName.setError(null);
            return true;
        }
    }

    // check the owner age over 18
    public boolean validAge() {
        int ownerage = Integer.parseInt(ownerAge.getText().toString());
        String check = ownerAge.getEditableText().toString().trim();
        if (ownerage < 18) {
            ownerAge.setError("you are under 18, so you can't sign up");
            return false;
        } else if (check.isEmpty()) {
            ownerAge.setError("enter your birthday");
            return false;
        } else {
            ownerAge.setError(null);
            return true;
        }
    }

    // this is the function for the select images from the phone
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        uploadImage.setImageURI(result);
                        imageUri = result;
                    }
                }
            });


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }

    public void checkButton(View v) {
        radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        ownergender = radioButton.getText().toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        }else {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
        }
    }


    // upload function, check all the input and go to next activity
    private void uploadPic() {

        ownerStates = mySpinner.getSelectedItem().toString();

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(ownerName.getText().toString() + "ProfilePic1"
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.child(ownerName.getText().toString() + "ProfilePic1"
                                    + "." + getFileExtension(imageUri)).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri downloadUri = task.getResult();
                                    Upload upload =
                                            new Upload(
                                                    userId.trim(),
                                                    ownerName.getText().toString().trim(),
                                                    ownergender.trim(),
                                                    ownerAge.getText().toString().trim(),
                                                    downloadUri.toString(),
                                                    ownerStates.trim(),
                                                    ownerBioEditText.getText().toString().trim(),
                                                    dogName,
                                                    dogBreed,
                                                    dogAge,
                                                    dogBio,
                                                    ownerLat,
                                                    ownerLong
                                            );
                                    db.collection("Users").document(userId)
                                            .set(upload)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "nice ");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                    Toast.makeText(OwnerProfile.this, "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OwnerProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        boolean isAgeValid = validAge();
        boolean isNameValid = validOwnerName();
        if (!isAgeValid | !isNameValid | ownergender == null | uploadImage == null) {
            return;
        }

        Intent intent = new Intent(OwnerProfile.this, Preference.class);
        //intent.putExtra(Constants.KEY_OWNER_GENDER, ownergender);
        startActivity(intent);
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

    public void ClickDogProfile(View view) {
        MainActivity.redirectActivity(this, DogProfilePage.class);
    }

    public void ClickOwnerProfile(View view) {
        recreate();
    }

    public void ClickChatMessaging(View view) {
        MainActivity.redirectActivity(this, CurrentUserActivity.class);
    }

    public void ClickLogout(View view) {
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}