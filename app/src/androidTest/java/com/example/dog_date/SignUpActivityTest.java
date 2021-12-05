package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.*;

import android.content.Intent;
import android.net.Uri;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SignUpActivityTest {

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityScenarioRule
            = new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.appTitle))
                .check(matches(withText(R.string.create_new_profile)));
    }

    @Test
    public void signUp() {
        onView(withId(R.id.username)).perform(typeText("MockUser"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("Asdfasdf1!"));
        closeSoftKeyboard();
    }
}
