package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    ".{8,}" +
                    "$");

    private EditText username;
    private EditText password;
    private EditText email;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    FirebaseFirestore fb;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        drawerLayout = findViewById(R.id.drawer_layout);
        username = (findViewById(R.id.username));
        password = (findViewById(R.id.password));
        email = (findViewById(R.id.email));

        mAuth = FirebaseAuth.getInstance();
    }

    public static boolean IsEmailValid(CharSequence c) {
        return !TextUtils.isEmpty(c) && Patterns.EMAIL_ADDRESS.matcher(c).matches();
    }

    public void SignUp(View view) {

        final String mEmail = email.getText().toString();
        final String mPassword = password.getText().toString();


        if (username.getText().toString().isEmpty()) {
            username.setError("Please Enter Username");
            return;
        } else if (!IsEmailValid(email.getText().toString())) {
            email.setError("Please Enter Valid Email");
            return;
        } else if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()) {
            password.setError("Password must contain: At least 8 characters, 1 number, 1 special character, 1 Upper and lower case letters");
            return;

        } else {
            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Oops! This email already has an account.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, DogProfile.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
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
        MainActivity.redirectActivity(this, DogProfile.class);
    }

    public void ClickOwnerProfile(View view) {
        MainActivity.redirectActivity(this, OwnerProfile.class);
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
