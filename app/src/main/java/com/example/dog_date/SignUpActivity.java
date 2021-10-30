package com.example.dog_date;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.bar_Login_Signup);
        setContentView(R.layout.activity_sign_up);


        drawerLayout = findViewById(R.id.drawer_layout);
        username = (findViewById(R.id.username));
        password = (findViewById(R.id.password));
        email = (findViewById(R.id.email));

    }

    public static boolean IsEmailValid(CharSequence c){
        return !TextUtils.isEmpty(c) && Patterns.EMAIL_ADDRESS.matcher(c).matches();
    }

    //checks form for empty fields
    public void SignUp(View view){
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
        MainActivity.redirectActivity(this, SwipeActivity.class);
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
