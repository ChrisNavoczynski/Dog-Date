package com.example.dog_date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class SwipeActivityTest {
    @Rule
    public ActivityScenarioRule<SwipeActivity> activityTestRule
            = new ActivityScenarioRule<>(SwipeActivity.class);

    @Test
    public void hasTextOnScreen() {

        onView(withId(R.id.appMatch))
                .check(matches(withText(R.string.today_s_matches)));
    }
}
