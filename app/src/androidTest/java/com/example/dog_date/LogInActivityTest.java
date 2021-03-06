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
    public void hasTextOnScreen() {
        onView(withId(R.id.appTitle))
                .check(matches(withText(R.string.welcome_back)));
    }
    @Test
    public void logIn() {
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("Asdfasdf1!"));
        closeSoftKeyboard();
    }

    @Test
    public void hasEmail() {
        onView(withId(R.id.email)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("Asdfasdf1!"));
        closeSoftKeyboard();
    }

    @Test
    public void hasPassword() {
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
    }

    @Test
    public void hasValidPassword() {
        onView(withId(R.id.email)).perform(typeText("mock@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("a"));
        closeSoftKeyboard();
    }
}
