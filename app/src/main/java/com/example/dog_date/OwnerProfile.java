package com.example.dog_date;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class OwnerProfile extends AppCompatActivity{

    private Uri imageUri;

    ImageView uploadImage;
    EditText ownerName, ownerAge;
    Button uploadButton,saveButton;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DrawerLayout drawerLayout;

    String ownername, ownerage, ownergender, ownerStates;
    Spinner mySpinner;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private StorageTask uploadTask;
    int radioId;

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
        drawerLayout = findViewById(R.id.drawer_layout);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profile");


        // upload button is not for upload but is select picture from the phone
        uploadButton.setOnClickListener(v -> mGetContent.launch("image/*"));

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
    public boolean validOwnerName(){
        ownername = ownerName.getEditableText().toString().trim();
        if (ownername.isEmpty()){
            ownerName.setError("enter your user name pls");
            return false;
        }else if(ownername.length()>15){
            ownerName.setError("username too long");
            return false;
        }else{
            ownerName.setError(null);
            return  true;
        }
    }

    // check the owner age over 18
    public boolean validAge(){
        int ownerage = Integer.parseInt(ownerAge.getText().toString());
        String check = ownerAge.getEditableText().toString().trim();
        if (ownerage < 18) {
            ownerAge.setError("you are under 18, so you can't sign up");
            return  false;
        } else if (check.isEmpty()) {
            ownerAge.setError("enter your birthday");
            return  false;
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
                    if (result !=null){
                        uploadImage.setImageURI(result);
                        imageUri = result;
                    }
                }
            });


    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }

    public void checkButton(View v){
        radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        ownergender = radioButton.getText().toString();
    }

    // upload function, check all the input and go to next activity
    private void uploadPic(){

        ownerStates = mySpinner.getSelectedItem().toString();

        if(imageUri != null){
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
                                    Upload upload = new Upload(ownerName.getText().toString().trim(),
                                            ownergender.trim(), ownerAge.getText().toString().trim(), downloadUri.toString(), ownerStates.trim());
                                    db.collection("Profiles").document("location").collection(ownerStates.trim()).document(ownerName.getText().toString().trim())
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
        if (!isAgeValid | !isNameValid | ownergender == null | uploadImage == null){
            return;
        }

        Intent intent = new Intent(OwnerProfile.this,Preference.class);
        ownername = ownerName.getText().toString();
        ownerage = ownerAge.getText().toString();
        intent.putExtra(Constants.KEY_OWNER_NAME, ownername);
        intent.putExtra(Constants.KEY_OWNER_AGE, ownerage);
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

    public void ClickDogProfile (View view) {
        MainActivity.redirectActivity(this, DogProfile.class);
    }

    public void ClickOwnerProfile (View view) {
        recreate();
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