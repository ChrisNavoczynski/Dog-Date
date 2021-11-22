package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dog_date.databinding.ActivityLogInBinding;
import com.example.dog_date.utilities.Constants;
import com.example.dog_date.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;
    private PreferenceManager preferenceManager;
    private EditText email;
    private EditText password;

    DrawerLayout drawerLayout;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(getApplicationContext(), CurrentUserActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        email = (findViewById(R.id.email));
        password = (findViewById(R.id.password));
        fb = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void setListeners() {
        binding.textSignUp.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.bLogIn.setOnClickListener(v -> {
            if (isValidLogInDetails()) {
                logIn();
            }
        });
    }

    public void logIn() {
        final String mEmail = email.getText().toString();
        final String mPassword = password.getText().toString();

        loading(true);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        mAuth.signInWithEmailAndPassword(mEmail, mPassword);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.email.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.password.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    showToast("Logged In!");
                   if(task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0) {
                       DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                       preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                       preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                       preferenceManager.putString(Constants.KEY_OWNER_NAME, documentSnapshot.getString(Constants.KEY_OWNER_NAME));
                       preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                       Intent intent = new Intent(getApplicationContext(), CurrentUserActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                   } else {
                       loading(false);
                       showToast("Log In Failed: Email or Password is incorrect");
                   }
                });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.bLogIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.bLogIn.setVisibility(View.INVISIBLE);

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

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidLogInDetails() {
        if (binding.email.getText().toString().trim().isEmpty()) {
            email.setError("Please Enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
            email.setError("Please enter valid email");
            return false;
        } else if (binding.password.getText().toString().trim().isEmpty()) {
            password.setError("Please Enter Password");
            return false;
        } else {
            return true;
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

    public void ClickChatMessaging (View view) {
        MainActivity.redirectActivity(this, CurrentUserActivity.class);
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
