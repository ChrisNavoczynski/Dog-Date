package com.example.dog_date;

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

public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUp> activityScenarioRule
            = new ActivityScenarioRule<>(SignUp.class);

    @Test
    public void usernameFieldRequired(){
        onView(withId(R.id.username)).perform(typeText(""));
        onView(withId(R.id.password)).perform(scrollTo(), typeText("somePassword"));
        onView(withId(R.id.email)).perform(scrollTo(), typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(scrollTo(), click());
        onView(allOf(withId(R.id.username), hasErrorText("Please Enter Username")));
    }

    @Test
    public void passwordFieldRequired(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        onView(withId(R.id.password)).perform(scrollTo(), typeText(""));
        onView(withId(R.id.email)).perform(scrollTo(), typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(scrollTo(), click());
        onView(allOf(withId(R.id.password), hasErrorText("Please Enter Password")));
    }

    @Test
    public void emailValidRequired(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        onView(withId(R.id.password)).perform(scrollTo(), typeText(""));
        onView(withId(R.id.email)).perform(scrollTo(), typeText("email"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(scrollTo(), click());
        onView(allOf(withId(R.id.password), hasErrorText("Please Enter Valid Email")));
    }

    @Test
    public void SignUpForm(){
        onView(withId(R.id.username)).perform(typeText("someUser"));
        onView(withId(R.id.password)).perform(scrollTo(), typeText("somePassword"));
        onView(withId(R.id.email)).perform(scrollTo(), typeText("email@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.b_signUp)).perform(scrollTo(), click());
    }

}
