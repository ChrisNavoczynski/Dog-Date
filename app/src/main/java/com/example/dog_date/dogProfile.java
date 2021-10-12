package com.example.dog_date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dog_date.databinding.ActivityMainBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class dogProfile extends AppCompatActivity{

    private Uri imageUri;

    ImageView uploadImage;
    EditText ownerName, ownerAge;
    CheckBox ownerMale,ownerFemale;
    Button uploadButton,saveButton;

    String ownername, ownerage, ownergender;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dog);

        uploadImage = findViewById(R.id.imageToUpload);
        uploadButton = findViewById(R.id.uploadImage);
        saveButton = findViewById(R.id.saveButton);
        ownerAge = findViewById(R.id.ownerAge);
        ownerMale = findViewById(R.id.ownerMale);
        ownerFemale = findViewById(R.id.ownerFemale);
        ownerName = findViewById(R.id.ownerName);

        storageReference = FirebaseStorage.getInstance().getReference("DisplayPics");

        // upload button is not for upload but is select picture from the phone
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        // save button gonna go to next activity and upload info to FireStore
        // TODO finish the upload function
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPic();
            }
        });

        // sprinner for the states input
        Spinner mySpinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(dogProfile.this,
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
            ownerAge.setError("enter your brithday");
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
                    }
                }
            });

    // upload function, check all the input and go to next activity
    private void uploadPic(){
        if (ownerMale.isChecked()){
            ownergender = ownerMale.getText().toString();
        }
        if (ownerFemale.isChecked()){
            ownergender = ownerFemale.getText().toString();
        }

        boolean isAgeValid = validAge();
        boolean isNameValid = validOwnerName();

        if (!isAgeValid | !isNameValid | ownergender ==null | uploadImage == null){
            return;
        }

        Intent intent = new Intent(com.example.dog_date.dogProfile.this,Preference.class);
        ownername = ownerName.getText().toString();
        ownerage = ownerAge.getText().toString();
        intent.putExtra(Constants.KEY_OWNER_NAME, ownername);
        intent.putExtra(Constants.KEY_OWNER_AGE, ownerage);
        intent.putExtra(Constants.KEY_OWNER_GENDER, ownergender);
        startActivity(intent);
    }
}