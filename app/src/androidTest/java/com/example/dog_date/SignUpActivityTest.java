package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SignUpActivityTest {
    @Rule
    public ActivityScenarioRule<SignUpActivity> activityScenarioRule
            = new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.appTitle))
                .check(matches(withText(R.string.create_new_profile)));
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
