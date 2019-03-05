package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.group;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class GroupActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    UserRepository mUserRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mUserRepository = new UserRepository(context);
        AppDatabase.reset();
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void Test() {
        List<User> users = mUserRepository.getAll();
        onView(withId(R.id.skipbutton_admin)).perform(click());
        onView(withId(R.id.buttongroup)).perform(click());
        for (User user : users) {
            if (!user.isAdmin()) onView(withText(user.getName())).check(matches(isDisplayed()));
        }
        Espresso.pressBack();
        User u = new User("test", "pass", false);
        for (User user : users) {
            if (!user.isAdmin()) mUserRepository.delete(user);
        }
        mUserRepository.add(u);
        onView(withId(R.id.buttongroup)).perform(click());
        onView(withId(R.id.grouprow_delete_button)).perform(click());
        assertEquals(4, mUserRepository.getAll().size());
        onView(withId(R.id.floatingActionButton)).perform(click());
    }
}