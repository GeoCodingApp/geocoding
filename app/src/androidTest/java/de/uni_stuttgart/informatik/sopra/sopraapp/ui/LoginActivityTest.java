package de.uni_stuttgart.informatik.sopra.sopraapp.ui;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.controllers.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private AppDatabase db;
    private LoginController loginController;

    @Test
    public void successfulAdminLoginTest() {
        // enter admin user name
        Espresso.onView(withId(R.id.username)).perform(typeText("admin"));
        // enter admin password
        Espresso.onView(withId(R.id.password)).perform(typeText("pass"));
        // click login
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        // check if we are in the admin hub
        Espresso.onView(withId(R.id.buttongroup)).check(matches(withText("Groups")));
    }

    @Test
    public void successfulUserLoginTest() {
        // enter user name
        Espresso.onView(withId(R.id.username)).perform(typeText("Gruppe1"));
        // enter user password
        Espresso.onView(withId(R.id.password)).perform(typeText("d7h8ic"));
        // click login
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        // check if we are in the user hub
        Espresso.onView(withId(R.id.toolbar_playerhub)).check(matches((isDisplayed())));
    }

    @Test
    public void failLoginTest() {
        // enter user name
        Espresso.onView(withId(R.id.username)).perform(typeText("dasdasd"));
        // enter user password
        Espresso.onView(withId(R.id.password)).perform(typeText("dasdasdas"));
        // click login
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        // check if we are in the user hub
        Espresso.onView(withId(R.id.password)).check(matches(hasErrorText("This password is incorrect")));
    }

    @Test
    public void invalidPasswordLoginTest() {
        // enter user name
        Espresso.onView(withId(R.id.username)).perform(typeText("dasdasd"));
        // enter user password
        Espresso.onView(withId(R.id.password)).perform(typeText("a"));
        // click login
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        // check if we are in the user hub
        Espresso.onView(withId(R.id.password)).check(matches(hasErrorText("This password is too short")));
    }

    @Test
    public void noNameLoginTest() {
        // click login
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
        // check if we are in the user hub
        Espresso.onView(withId(R.id.username)).check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void skipAdminLoginTest() {
        Espresso.onView(withId(R.id.skipbutton_admin)).perform(click());
        // check if we are in the admin hub
        Espresso.onView(withId(R.id.buttongroup)).check(matches(withText("Groups")));
    }

    @Test
    public void skipUserLoginTest() {
        Espresso.onView(withId(R.id.skipbutton_participant)).perform(click());
        // check if we are in the user hub
        Espresso.onView(withId(R.id.toolbar_playerhub)).check(matches((isDisplayed())));
    }
}