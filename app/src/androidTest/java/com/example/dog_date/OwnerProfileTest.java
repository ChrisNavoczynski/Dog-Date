package com.example.dog_date;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class OwnerProfileTest {

    private FirebaseAuth mAuth;

    private FirebaseUser firebaseUser;

    @Rule
    public ActivityScenarioRule<OwnerProfile> activityScenarioRule
            = new ActivityScenarioRule<OwnerProfile>(OwnerProfile.class);

    @Test
    public void checkRadio() {
        onView(withId(R.id.Male))
                .perform(click());

        onView(withId(R.id.Male))
                .check(matches(isChecked()));

        onView(withId(R.id.Female))
                .check(matches(isNotChecked()));
    }


    @Test
    public void canEnterNameAndSignUp(){
        onView(withId(R.id.ownerName)).perform(typeText("jj huang"));
        onView(withId(R.id.ownerName)).check(matches(withText("jj huang")));

        onView(withId(R.id.ownerAge)).perform(typeText("99"),closeSoftKeyboard());
        onView(withId(R.id.ownerAge)).check(matches(withText("99")));
    }
}
