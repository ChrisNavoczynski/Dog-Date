package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login_SignupActivity extends AppCompatActivity {

    private Button mLog_in;
    private Button mSign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.bar_Login_Signup);
        setContentView(R.layout.activity_login_signup);

        mLog_in = (Button)findViewById(R.id.b_logIn);
        mSign_up = (Button)findViewById(R.id.b_signUp);

        mLog_in.setOnClickListener(view -> {
            Intent intent = new Intent(Login_SignupActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
            return;
        });

        mSign_up.setOnClickListener(view -> {
            Intent intent = new Intent(Login_SignupActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
            return;
        });


    }
}
