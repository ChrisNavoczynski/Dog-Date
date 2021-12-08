package com.example.dog_date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PreferenceTest {

    @Rule
    public ActivityScenarioRule<Preference> activityTestRule
            = new ActivityScenarioRule<Preference>(Preference.class);

    @Test
    public void checkRadio() {
        onView(withId(R.id.dog_size_small_id))
                .perform(click());

        onView(withId(R.id.dog_size_small_id))
                .check(matches(isChecked()));

        onView(withId(R.id.dog_size_medium_id))
                .check(matches(isNotChecked()));

        onView(withId(R.id.dog_size_large_id))
                .check(matches(isNotChecked()));
    }

    @Test
    public void checkSizeRadio() {
        onView(withId(R.id.Male))
                .perform(click());

        onView(withId(R.id.Male))
                .check(matches(isChecked()));

        onView(withId(R.id.Female))
                .check(matches(isNotChecked()));
    }

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.title))
                .check(matches(withText("Choose Your Preference")));
    }

    @Test
    public void dogBreedTest(){
        onView(withId(R.id.dog_breed_text_id)).perform(typeText("Aidi"));

        onView((withId(R.id.dog_breed_text_id))).check(matches(withText("Aidi")));
    }

    @Test
    public void dogAgeTest(){
        onView(withId(R.id.dogMaxAge)).perform(typeText("10"),closeSoftKeyboard());
        onView(withId(R.id.dogMaxAge)).check(matches(withText("10")));

        onView(withId(R.id.dogMinAge)).perform(typeText("2"),closeSoftKeyboard());
        onView(withId(R.id.dogMinAge)).check(matches(withText("2")));
    }
}
