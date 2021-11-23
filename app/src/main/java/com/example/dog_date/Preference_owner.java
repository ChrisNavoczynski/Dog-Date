package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Preference_owner extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RadioGroup genderGroup;
    RadioButton genderButton;
    EditText ownerMaxAge, ownerMinAge;
    Spinner spinner;
    Button b_save;

    String ownerGenderP;

    int genderRadioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_owner);

        drawerLayout = findViewById(R.id.drawer_layout);

        genderGroup = findViewById(R.id.genderGroup);
        ownerMaxAge = findViewById(R.id.ownerMaxAge);
        ownerMinAge = findViewById(R.id.ownerMinAge);
        b_save = findViewById(R.id.save);

        spinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Preference_owner.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

    }

    public void checkButton(View v){
        genderRadioId = genderGroup.getCheckedRadioButtonId();
        genderButton = findViewById(genderRadioId);
        ownerGenderP = genderButton.getText().toString();
    }

    public void gotoP2(View v){


        if((genderGroup.getCheckedRadioButtonId() == -1)){
            Toast.makeText(Preference_owner.this, "Select Preferred Gender", Toast.LENGTH_LONG).show();
            return;
        }
        if(ownerMaxAge.getText().toString().isEmpty()){
            Toast.makeText(Preference_owner.this, "Enter Preferred Max Age", Toast.LENGTH_LONG).show();
            return;
        }
        if(ownerMinAge.getText().toString().isEmpty()){
            Toast.makeText(Preference_owner.this, "Enter Preferred Min Age", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent2 = new Intent(com.example.dog_date.Preference_owner.this,com.example.dog_date.DogProfilePage.class);
        startActivity(intent2);
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