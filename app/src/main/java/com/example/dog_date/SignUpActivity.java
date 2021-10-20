package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (findViewById(R.id.username));
        password = (findViewById(R.id.password));
        email = (findViewById(R.id.email));

    }

    public static boolean IsEmailValid(CharSequence c){
        return !TextUtils.isEmpty(c) && Patterns.EMAIL_ADDRESS.matcher(c).matches();
    }

    //checks form for empty fields
    public void SignUp(View view){
        this.view = view;
        if(username.getText().toString().isEmpty()){
            username.setError("Please Enter Username");
            return;
        }
        if(!IsEmailValid(email.getText())){
            email.setError("Please Enter Valid Email");
            return;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Please Enter Password");
            return;
        }

        Intent intent = new Intent(this, DoggyProfile.class);

        startActivity(intent);
    }
}
