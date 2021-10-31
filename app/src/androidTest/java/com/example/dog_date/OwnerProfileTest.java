package com.example.dog_date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class OwnerProfileTest {
    @Rule
    public ActivityScenarioRule<OwnerProfile> activityTestRule
            = new ActivityScenarioRule<>(OwnerProfile.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.dog_profile_title))
                .check(matches(withText("Your Profile")));
    }

    @Test
    public void openDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
    }
}
