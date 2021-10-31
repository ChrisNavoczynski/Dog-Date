package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
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
    public void buttonPressLogin() {
        closeSoftKeyboard();
        onView(withId(R.id.b_logIn)).check(matches(ViewMatchers.withText(R.string.log_in)));
    }

    @Test
    public void buttonPressSignUp() {
        closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).check(matches(ViewMatchers.withText(R.string.sign_up)));
    }
}