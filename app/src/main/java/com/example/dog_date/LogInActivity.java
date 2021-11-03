package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LogInActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;

    DrawerLayout drawerLayout;

    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = (findViewById(R.id.username));
        password = (findViewById(R.id.password));

        fb = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);

    }


    public void LogIn (View view){
        if(username.getText().toString().isEmpty()){
            username.setError("Please Enter Username");
            return;
        }
        if (password.getText().toString().equals("")){
            password.setError("Please Enter Password");
            return;
        }

        fb.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String user = doc.getString("username");
                            String p = doc.getString("Password");
                            String user1 = username.getText().toString().trim();
                            String p1 = password.getText().toString().trim();

                            if (user.equals(user1) & p.equals(p1)) {
                                Intent intent = new Intent(LogInActivity.this, SwipeActivity.class);
                                startActivity(intent);
                                Toast.makeText(LogInActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                Toast.makeText(LogInActivity.this, "Incorrect Log In info. Please try again", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    }
                });

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

    public void ClickDogProfile (View view) {
        MainActivity.redirectActivity(this, DogProfile.class);
    }

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

