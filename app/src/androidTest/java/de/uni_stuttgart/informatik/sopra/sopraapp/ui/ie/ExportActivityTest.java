package de.uni_stuttgart.informatik.sopra.sopraapp.ui.ie;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.controllers.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * @author MikeAshi
 */
public class ExportActivityTest {
    @Rule
    public ActivityTestRule<ExportActivity> mActivityTestRule = new ActivityTestRule<>(ExportActivity.class, false, false);
    private AppDatabase db;
    private LoginController loginController;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        UserRepository userRepository = new UserRepository(context);
        AppDatabase.reset();
        loginController = LoginController.getInstance(context);
        loginController.login("admin", "pass");
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void testAdmin() {
        Intent intent = new Intent();
        Intents.init();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.event_spinier)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("StuttgartCampusEvent"))).perform(click());
        intending(hasAction(Intent.ACTION_CHOOSER)).respondWith(new Instrumentation.ActivityResult(0, null));
        onView(withId(R.id.button_export)).perform(click());
        intended(hasAction(Intent.ACTION_CHOOSER));
        Intents.release();
    }
}