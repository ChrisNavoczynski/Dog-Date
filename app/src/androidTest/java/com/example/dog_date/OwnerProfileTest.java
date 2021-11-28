package com.example.dog_date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class OwnerProfileTest {
    @Rule
    public ActivityScenarioRule<OwnerProfile> activityTestRule
            = new ActivityScenarioRule<>(OwnerProfile.class);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.profileOwner))
                .check(matches(withText(R.string.your_profile)));
    }

/*    @Test
    public void openDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
    }*/
}
