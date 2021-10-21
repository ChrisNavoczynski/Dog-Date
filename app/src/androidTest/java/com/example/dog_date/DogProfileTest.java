package com.example.dog_date;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DogProfileTest {

    @Rule
    public ActivityScenarioRule<DoggyProfile> activityTestRule
            = new ActivityScenarioRule<>(DoggyProfile.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.dog_profile_title))
                .check(matches(withText("Create your dog's profile")));
    }

    @Test
    public void fillForm() {
        onView(withId(R.id.dog_name_text_id)).perform(typeText("Jasper Doggo"));
        closeSoftKeyboard();
        onView(withId(R.id.dog_breed_text_id)).perform(typeText("Pembroke Welsh Corgi"));
        closeSoftKeyboard();
        onView(withId(R.id.dog_gender_male_id)).perform(click());
        onView(withId(R.id.dog_age_text_id)).perform(typeText("8"));
        closeSoftKeyboard();
        onView(withId(R.id.dog_size_medium_id)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.nextButton)).check(matches(ViewMatchers.withText(R.string.next_button_text)));
    }

}
