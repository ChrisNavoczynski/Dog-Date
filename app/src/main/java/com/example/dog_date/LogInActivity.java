package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class LogInActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;

    DrawerLayout drawerLayout;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = (findViewById(R.id.email));
        password = (findViewById(R.id.password));

        fb = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);

        mAuth = FirebaseAuth.getInstance();
    }


    public void LogIn(View view) {

        final String mEmail = email.getText().toString();
        final String mPassword = password.getText().toString();

        if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter Email");
            return;
        } else if (password.getText().toString().equals("")) {
            password.setError("Please Enter Password");
            return;
        } else {

            mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LogInActivity.this, "Log In Failed: Email or Password is incorrect",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(LogInActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInActivity.this, Preference.class);
                               // Intent intent = new Intent(LogInActivity.this, SwipeActivity.class);
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
        recreate();
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
