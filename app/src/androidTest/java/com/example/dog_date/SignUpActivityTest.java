package com.example.dog_date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
    public void usernameFieldRequired(){
        onView(withId(R.id.username)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("somePassword"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(click());
        onView(allOf(withId(R.id.username), hasErrorText("Please Enter Username")));
    }

    @Test
    public void passwordFieldRequired(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"));
        closeSoftKeyboard();
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(click());
        onView(allOf(withId(R.id.password), hasErrorText("Please Enter Password")));
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
        onView(allOf(withId(R.id.password), hasErrorText("Please Enter Valid Email")));
    }

    @Test
    public void SignUpForm(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("somePassword"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(click());
    }

}
