package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;

    FirebaseFirestore fb;
    DocumentReference dRef;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        drawerLayout = findViewById(R.id.drawer_layout);
        username = (findViewById(R.id.username));
        password = (findViewById(R.id.password));
        email = (findViewById(R.id.email));

        fb = FirebaseFirestore.getInstance();
        dRef = fb.collection("users").document();
    }

    public static boolean IsEmailValid(CharSequence c){
        return !TextUtils.isEmpty(c) && Patterns.EMAIL_ADDRESS.matcher(c).matches();
    }

    public void SignUp(View view){
        if(username.getText().toString().isEmpty()){
            username.setError("Please Enter Username");
            return;
        }else if(!IsEmailValid(email.getText().toString())){
            email.setError("Please Enter Valid Email");
            return;
        }else if(password.getText().toString().equals("")){
            password.setError("Please Enter Password");
            return;
        }else{
            dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        Toast.makeText(SignUpActivity.this, "This user exsits", Toast.LENGTH_SHORT).show();
                    } else{
                        Map<String, Object> account = new HashMap<>();
                        account.put("username", username.getText().toString());
                        account.put("Email", email.getText().toString());
                        account.put("Password", password.getText().toString());

                        fb.collection("users")
                                .add(account)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Oops! Something went wrong.", e.getMessage());
                                    }
                                });
                    }
                }
            });
        }
        Intent intent = new Intent(this, DogProfile.class);

        startActivity(intent);
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
