package com.espresso.pongmile.espresso;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.espresso.pongmile.espresso.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

@LargeTest

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void strat(){
        File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
        String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
        for (String fileName : sharedPreferencesFileNames) {
            InstrumentationRegistry.getTargetContext().getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
        }
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void NameAndAgeIsNull() {
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.message), withText("Please Enter user info"), isDisplayed()));
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }
    @Test
    public void NameIsNullAge20() {
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.message), withText("Please Enter user info"), isDisplayed()));
        onView(allOf(withId(android.R.id.button1))).perform(click());

    }

    @Test
    public void ListNull() {
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText(""), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
        onView(allOf(withId(R.id.buttonGotoList))).perform(click());
        onView(allOf(withId(R.id.textNotFound))).check(matches(isDisplayed()));
    }

    @Test
    public void  NameYingAndAgeIsNull(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.message), withText("Please Enter user info"), isDisplayed()));
    }

    @Test
    public void NameYingAndAge20(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.buttonGotoList))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.textName)).check(matches(withText("Ying")));
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.textAge)).check(matches(withText("20")));
        SystemClock.sleep(1000);
        onView(withId(R.id.clearList)).perform(click());
    }

    @Test
    public void NameLadaratAndAge20(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ladarat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.buttonGotoList))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(1, R.id.textName)).check(matches(withText("Ladarat")));
        onView(withRecyclerView(R.id.list).atPositionOnView(1, R.id.textAge)).check(matches(withText("20")));
        SystemClock.sleep(1000);
        onView(withId(R.id.clearList)).perform(click());
    }

    @Test
    public void NameSomkiatAndAge80(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ladarat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Somkiat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("80"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.buttonGotoList))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(2, R.id.textName)).check(matches(withText("Somkiat")));
        onView(withRecyclerView(R.id.list).atPositionOnView(2, R.id.textAge)).check(matches(withText("80")));
        SystemClock.sleep(1000);
        onView(withId(R.id.clearList)).perform(click());
    }

    @Test
    public void NamePrayochAndAge60(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ladarat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Somkiat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("80"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Prayoch"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("60"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.buttonGotoList))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(3, R.id.textName)).check(matches(withText("Prayoch")));
        onView(withRecyclerView(R.id.list).atPositionOnView(3, R.id.textAge)).check(matches(withText("60")));
        SystemClock.sleep(1000);
        onView(withId(R.id.clearList)).perform(click());
    }

    @Test
    public void NamePrayochAndAge50(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ladarat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Somkiat"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("80"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Prayoch"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("60"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Prayoch"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("50"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.buttonGotoList))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(4, R.id.textName)).check(matches(withText("Prayoch")));
        onView(withRecyclerView(R.id.list).atPositionOnView(4, R.id.textAge)).check(matches(withText("50")));
        SystemClock.sleep(1000);
        onView(withId(R.id.clearList)).perform(click());
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
