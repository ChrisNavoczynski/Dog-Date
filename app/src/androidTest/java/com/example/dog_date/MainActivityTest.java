package com.example.dog_date;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.view.Gravity;

import com.example.dog_date.utilities.Constants;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void hasTextOnScreen() {

        onView(withId(R.id.welcome))
                .check(matches(withText(R.string.welcome)));

        onView(withId(R.id.appTitle))
                .check(matches(withText(R.string.dog_date)));
    }

    @Test
    public void buttonPress() {
        closeSoftKeyboard();
        onView(withId(R.id.beginButton)).check(matches(ViewMatchers.withText(R.string.lets_begin_btn)));
    }

    @Test
    public void canGoToLoginSignUpActivity() {
        try {
            Intents.init();
            onView(withId(R.id.beginButton)).perform(click());
            intended(hasComponent(Login_SignupActivity.class.getName()));
        } finally {
            Intents.release();
        }
    }

    @Test
    public void openDogProfileDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withText("Dog Profile")).perform(click());
        onView(withId(R.id.dog_profile_title)).check(matches(withText("Create your dog's profile")));
    }

/*    @Test
    public void openOwnerProfileDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withText("Owner Profile")).perform(click());
        onView(withId(R.id.profileOwner)).check(matches(withText("Your Profile")));
    }*/
}
