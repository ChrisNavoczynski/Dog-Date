package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.view.View;
import android.widget.ImageView;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
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

    @Test
    public void hasUserName(){
        onView(withId(R.id.username)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("Asdfasdf1!"));
        closeSoftKeyboard();
//        onView(withId(R.id.b_signUp)).perform(click());
//        onView(allOf(withId(R.id.username), hasErrorText("Please Enter Username")));
    }

    @Test
    public void hasEmail(){
        onView(withId(R.id.username)).perform(typeText("MockUser"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("Asdfasdf1!"));
        closeSoftKeyboard();
//        onView(withId(R.id.b_signUp)).perform(click());
//        onView(allOf(withId(R.id.username), hasErrorText("Please Enter Valid Email")));
    }


    @Test
    public void hasValidEmail(){
        onView(withId(R.id.username)).perform(typeText("MockUser"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("m"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("Asdfasdf1!"));
        closeSoftKeyboard();
//        onView(withId(R.id.b_signUp)).perform(click());
//        onView(allOf(withId(R.id.username), hasErrorText("Please Enter Valid Email")));
    }

    @Test
    public void hasPassword(){
        onView(withId(R.id.username)).perform(typeText("MockUser"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
//        onView(withId(R.id.b_signUp)).perform(click());
//        onView(allOf(withId(R.id.username), hasErrorText("Password must contain: At least 8 characters, 1 number, 1 special character, 1 Upper and lower case letters")));
    }

    @Test
    public void hasValidPassword(){
        onView(withId(R.id.username)).perform(typeText("MockUser"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("A"));
        closeSoftKeyboard();
//        onView(withId(R.id.b_signUp)).perform(click());
//        onView(allOf(withId(R.id.username), hasErrorText("Password must contain: At least 8 characters, 1 number, 1 special character, 1 Upper and lower case letters")));
    }

}
