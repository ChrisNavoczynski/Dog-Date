package com.example.dog_date;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static net.bytebuddy.matcher.ElementMatchers.is;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PreferenceOwnerTest {

    private static final String LAST_ITEM_ID = "item: 99";

    @Rule
    public ActivityScenarioRule<Preference_owner> activityTestRule
            = new ActivityScenarioRule<Preference_owner>(Preference_owner.class);


    @Test
    public void checkRadio() {
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
    public void ownerAgeTest(){
        onView(withId(R.id.ownerMaxAge)).perform(typeText("60"),closeSoftKeyboard());
        onView(withId(R.id.ownerMaxAge)).check(matches(withText("60")));

        onView(withId(R.id.ownerMinAge)).perform(typeText("20"),closeSoftKeyboard());
        onView(withId(R.id.ownerMinAge)).check(matches(withText("20")));
    }

    @Test
    public void FirstItem_Displayed() {

        onView(withText(LAST_ITEM_ID)).check(doesNotExist());
    }

    @Test
    public void list_Scrolls() {
        onData(anything()).inAdapterView(withId(R.id.spinner1)).atPosition(2).perform(click());
    }
}
