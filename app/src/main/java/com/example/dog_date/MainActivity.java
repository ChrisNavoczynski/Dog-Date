package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.beginButton);

        btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,
                SignUpActivity.class)));
    }


}