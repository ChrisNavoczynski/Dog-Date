package com.example.dog_date;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.Match.MatchActivity;
import com.example.dog_date.utilities.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.location.LocationManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwipeActivity extends AppCompatActivity {

    private Upload users_data[];
    private arrayAdapter arrayAdapter;
    private int i;
    DrawerLayout drawerLayout;
    boolean nope = false;

    private FirebaseAuth mAuth;
    LocationManager locationManager;
    Location userLoc;
    FusedLocationProviderClient fusedLocationProviderClient;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    String ownername, ownerage, ownergender, ownerStates, currentUser, currentUserState, ownerImage;
    double ownerLat, ownerLong,currentLat, currentLong, currentMax;
    ListView listView;
    List<Upload> rowItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_activity);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                SwipeActivity.this
        );

        drawerLayout = findViewById(R.id.drawer_layout);
        rowItem = new ArrayList<Upload>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        userLoc = new Location(getProviderName());
        gpsUpdates();

        getMatch();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItem);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItem.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Upload user = (Upload) dataObject;
                String userID = user.getUserID();

                Map<String, Object> disLikeIt = new HashMap<>();
                disLikeIt.put("userID", userID);

                DocumentReference userDb = db.collection("Users").document(currentUser);
                userDb.collection("Nope")
                        .add(disLikeIt)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SwipeActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Upload user = (Upload) dataObject;
                String userID = user.getUserID();

                Map<String, Object> likeIt = new HashMap<>();
                likeIt.put("userID", userID);

                DocumentReference userDb = db.collection("Users").document(currentUser);
                userDb.collection("Yeah")
                        .add(likeIt)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SwipeActivity.this, "you like this person", Toast.LENGTH_SHORT).show();
                            }
                        });
                isMatch(userID);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Upload user = (Upload) dataObject;
                ownername = user.getOwnerName();
                ownerage = user.getOwnerAge();
                ownergender = user.getOwnerGender();
                ownerImage = user.getmImageUrl();
                ownerLat = user.getLatitude();
                ownerLong = user.getLongitude();
                ownerStates = user.getOwnerStates();

                Toast.makeText(SwipeActivity.this, "Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SwipeActivity.this, ClickProfile.class);
                intent.putExtra("ownerName", ownername);
                intent.putExtra("ownerAge", ownerage);
                intent.putExtra("ownerGender", ownergender);
                intent.putExtra("ownerStates", ownerStates);
                intent.putExtra("ownerImage", ownerImage);
                intent.putExtra("ownerLat", ownerLat);
                intent.putExtra("ownerLong", ownerLong);
                startActivity(intent);
            }
        });

    }

    public void gpsUpdates() {
        if (ActivityCompat.checkSelfPermission(SwipeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(SwipeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            locationManager.requestLocationUpdates(getProviderName(), 5000, 50, locationListener);
        }

    }

    String getProviderName() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.

        // Provide your criteria and flag enabledOnly that tells
        // LocationManager only to return active providers.
        return locationManager.getBestProvider(criteria, true);
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            userLoc.setLatitude(location.getLatitude());
            userLoc.setLongitude(location.getLongitude());
            Log.i(TAG, "here is not the right path " + userLoc.getLatitude() + " and " + userLoc.getLongitude());
            DocumentReference profileRef = db.collection("Users").document(currentUser);
            Map<String, Object> data = new HashMap<>();
            data.put("latitude",userLoc.getLatitude());
            data.put("longitude",userLoc.getLongitude());
            profileRef.update(data);
            getMatch();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };
    /*private void gpsUpdate() {
        if (ActivityCompat.checkSelfPermission(SwipeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(SwipeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "here is the right path ");
            getCurrentLocation();
        }else{
            Log.i(TAG, "here is not the right path ");
            ActivityCompat.requestPermissions(SwipeActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
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
                        DocumentReference profileRef = db.collection("Users").document(currentUser);
                        Map<String, Object> data = new HashMap<>();
                        data.put("latitude", ownerLat);
                        data.put("longitude", ownerLong);
                        profileRef.update(data);
                    }else{
                        LocationRequest locationRequest = new LocationRequest()
                                .setInterval(1000)
                                .setFastestInterval(100)
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                ownerLat = locationResult.getLastLocation().getLatitude();
                                ownerLong = locationResult.getLastLocation().getLongitude();
                                DocumentReference profileRef = db.collection("Users").document(currentUser);
                                Map<String, Object> data = new HashMap<>();
                                data.put("latitude", ownerLat);
                                data.put("longitude", ownerLong);
                                profileRef.update(data);
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
    }*/

    private void getMatch() {

        DocumentReference currentUserDB = db.collection("Users").document(currentUser);
        currentUserDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ownerStates = documentSnapshot.getString("ownerStates");
                currentLat = documentSnapshot.getDouble("latitude");
                currentLong = documentSnapshot.getDouble("longitude");
                currentMax = documentSnapshot.getDouble("maxRange");

                CollectionReference collectionReference = db.collection("Users");

                Query userQuery = collectionReference.whereEqualTo("ownerStates", ownerStates);

                userQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Upload user = documentSnapshot.toObject(Upload.class);
                            float[] dist = new float[1];
                            Location.distanceBetween(currentLat, currentLong, user.getLatitude(),user.getLongitude(),dist);

                            String userID = user.getUserID();

                            Log.i(TAG, "nope in here what is that now " + nope);

                            if (!userID.equals(currentUser) && currentMax > dist[0]*0.000621371192) {
                                rowItem.add(user);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }

    private void isMatch(String userID) {
        CollectionReference collectionReference = db.collection("Users").document(userID).collection("Yeah");
        Query likeQuery = collectionReference.whereEqualTo("userID", currentUser);

        likeQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String uID = documentSnapshot.getId();
                    addToMatch(uID, userID);
                    Log.i(TAG, "New Match!!!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "no one likes you man T.T");
            }
        });
    }

    private void addToMatch(String key, String userID) {
        Map<String, Object> chatID = new HashMap<>();
        chatID.put("chatWithUser", userID);
        chatID.put("key", key);
        DocumentReference currentUserDB = db.collection("Users").document(currentUser);
        currentUserDB.collection(Constants.KEY_COLLECTION_CHAT)
                .add(chatID)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "add to chat");
                    }
                });
        Map<String, Object> chat2ID = new HashMap<>();
        DocumentReference userDB = db.collection("Users").document(userID);
        chat2ID.put("chatWithUser", currentUser);
        chat2ID.put("key", key);
        userDB.collection(Constants.KEY_COLLECTION_CHAT)
                .add(chat2ID)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "add to chat 2");
                    }
                });
    }

    private boolean checkNope(String userID) {

        CollectionReference currentUserDb = db.collection("Users").document(currentUser).collection("Nope");
        Query disLikeQuery = currentUserDb.whereEqualTo("userID", userID);

        disLikeQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                nope = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "not in here");
            }
        });

        CollectionReference currentUser2Db = db.collection("Users").document(currentUser).collection(Constants.KEY_COLLECTION_CHAT);
        Query likeQuery = currentUserDb.whereEqualTo("userID", userID);

        likeQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                nope = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "not in here");
            }
        });

        Log.i(TAG, "nope is here" + nope);

        return nope;
    }


    public void goToMatch(View view) {
        Intent intent = new Intent(SwipeActivity.this, MatchActivity.class);
        startActivity(intent);
        return;
    }


    public void ClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickDogProfile(View view) {
        MainActivity.redirectActivity(this, DogProfilePage.class);
    }

    public void ClickOwnerProfile(View view) {
        MainActivity.redirectActivity(this, MatchActivity.class);
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