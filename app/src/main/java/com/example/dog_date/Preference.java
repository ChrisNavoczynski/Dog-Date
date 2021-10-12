package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Preference extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
    }

    public void gotoP2(){
        Intent intent = new Intent(com.example.dog_date.Preference.this,Preference_owner.class);
        startActivity(intent);
    }
}
