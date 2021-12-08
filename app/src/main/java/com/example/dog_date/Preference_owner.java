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

import com.example.dog_date.models.OwnerPreferenceItems;
import com.example.dog_date.ViewModels.OwnerPreferenceViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Preference_owner extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RadioGroup genderGroup;
    RadioButton genderButton;
    EditText ownerMaxAge, ownerMinAge;
    Spinner spinner;
    Button b_save;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    String userId, ownerGenderP, breedP, dogSizeP, dogMaxAgeP, dogMinAgep, genderDogP;

    private OwnerPreferenceViewModel vm;

    int genderRadioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_owner);

        vm = new OwnerPreferenceViewModel();

        drawerLayout = findViewById(R.id.drawer_layout);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

    public void goToProfile(View v){
        userId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b.containsKey("breedP") && b.containsKey("dogMaxAgeP") && b.containsKey("dogMinAgep") && b.containsKey("dogSizeP") && b.containsKey("genderDogP")) {
            breedP = b.getString("breedP");
            dogMaxAgeP = b.getString("dogMaxAgeP");
            dogMinAgep = b.getString("dogMinAgep");
            dogSizeP = b.getString("dogSizeP");
            genderDogP = b.getString("genderDogP");
        }

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

        OwnerPreferenceItems ownerPreferenceItems = new OwnerPreferenceItems(ownerGenderP.trim(), ownerMaxAge.getText().toString(), ownerMinAge.getText().toString(), userId, genderDogP.trim(), dogSizeP.trim(), breedP.trim(), dogMaxAgeP.toString(), dogMinAgep.toString());
        vm.addOwnerPreferences(ownerPreferenceItems);

        Intent intent2 = new Intent(com.example.dog_date.Preference_owner.this,com.example.dog_date.DogProfilePage.class);
        startActivity(intent2);
    }

}