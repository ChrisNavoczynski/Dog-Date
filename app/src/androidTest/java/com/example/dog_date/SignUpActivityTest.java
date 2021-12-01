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

//    @Before
//    public void init() {
//        firebaseAuth = mock(FirebaseAuth.class);
//        firebaseUser = mock(FirebaseUser.class);
//
//        FirebaseAuthGetter.setFirebaseAuth(firebaseAuth);
//        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
//        when(firebaseUser.getDisplayName()).thenReturn("Mock User");
//        when(firebaseUser.getPhotoUrl()).thenReturn(Uri.parse("https://images.unsplash.com/photo-1521247560470-d2cbfe2f7b47?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1740&q=80"));
//        when(firebaseUser.getEmail()).thenReturn("mock@gmail.com");
//    }

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

//        onView(withId(R.id.imageProfile)).perform(click());
    }

/*    @Test
    public void usernameFieldRequired(){
        onView(withId(R.id.username)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("somePassword"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(click());
        onView(allOf(withId(R.id.imageProfile), hasErrorText("Profile Image Required")));
    }

    @Test
    public void emailValidRequired(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("email"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(click());
        onView(allOf(withId(R.id.imageProfile), hasErrorText("Profile Image Required")));
    }

    @Test
    public void SignUpForm() {
        onView(withId(R.id.username)).perform(typeText("someUser"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("somePassword"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(click());
        onView(allOf(withId(R.id.imageProfile), hasErrorText("Profile Image Required")));
    }*/
}
