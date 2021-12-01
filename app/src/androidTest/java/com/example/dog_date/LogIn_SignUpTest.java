package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class LogIn_SignUpTest {
    @Rule
    public ActivityScenarioRule<Login_SignupActivity> activityTestRule
            = new ActivityScenarioRule<>(Login_SignupActivity.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.appTitle))
                .check(matches(withText(R.string.dog_date)));
    }

    @Test
    public void openDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
    }

    @Test
    public void canGoToLogInActivity() {
        try {
            Intents.init();
            onView(withId(R.id.b_logIn)).perform(click());
            intended(hasComponent(LogInActivity.class.getName()));
        } finally {
            Intents.release();
        }
    }

    @Test
    public void canGoToSignUpActivity() {
        try {
            Intents.init();
            onView(withId(R.id.b_signUp)).perform(click());
            intended(hasComponent(SignUpActivity.class.getName()));
        } finally {
            Intents.release();
        }
    }
}
