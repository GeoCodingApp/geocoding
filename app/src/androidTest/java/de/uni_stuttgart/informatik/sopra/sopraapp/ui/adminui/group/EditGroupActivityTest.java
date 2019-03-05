package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.group;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EditGroupActivityTest {
    @Rule
    public ActivityTestRule<EditGroupActivity> mActivityTestRule = new ActivityTestRule<>(EditGroupActivity.class, true, false);
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
    public void save() {
        User u = mUserRepository.getAll().get(0);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("name", u.getName());
        intent.putExtra("pw", u.getPassword());
        intent.putExtra("id", u.getId());
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.editgroup_savebutton)).perform(click());
    }

    @Test
    public void delete() {
        User u = mUserRepository.getAll().get(0);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("name", u.getName());
        intent.putExtra("pw", u.getDecryptedPassword());
        intent.putExtra("id", u.getId());
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.editgroup_deletebutton)).perform(click());
    }
}