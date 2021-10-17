package com.example.dog_date;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DogProfileTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.dog_profile_title))
                .check(matches(withText(R.string.create_your_dogs_profile)));
    }

    @Test
    public void fillForm() throws InterruptedException {
        onView(withId(R.id.dog_name_text_id)).perform(typeText("Jasper Doggo"));
        onView(withId(R.id.autoCompleteTextView)).perform(typeText("Pembroke Welsh Corgi"), (scrollTo()), (click()));
        onView(withId(R.id.dog_gender_male_id)).perform(scrollTo(), click());
        onView(withId(R.id.dog_age_text_id)).perform(typeText("8"));
        onView(withId(R.id.dog_size_medium_id)).perform(scrollTo(), click());
        onView(withId(R.id.nextButton)).perform(scrollTo(), click());
    }

}
