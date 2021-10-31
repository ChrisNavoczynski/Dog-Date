package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.view.Gravity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class LogInActivityTest {

    @Rule
    public ActivityScenarioRule<LogInActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LogInActivity.class);

    @Test
    public void usernameFieldRequired() {
        onView(withId(R.id.username)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("somePassword"));
        closeSoftKeyboard();
        onView(withId(R.id.b_logIn)).perform(click());
        onView(allOf(withId(R.id.username), hasErrorText("Please Enter Username")));
    }

    @Test
    public void passwordFieldRequired(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.b_logIn)).perform(click());
        onView(allOf(withId(R.id.password), hasErrorText("Please Enter Password")));
    }

    @Test
    public void openDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
    }

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.appTitle))
                .check(matches(withText(R.string.dog_date)));
    }
}
