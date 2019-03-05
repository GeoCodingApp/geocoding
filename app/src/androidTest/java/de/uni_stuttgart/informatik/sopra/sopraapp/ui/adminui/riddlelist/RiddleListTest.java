package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist;

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
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.LoginActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class RiddleListTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    PuzzleListRepository mPuzzleListRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mPuzzleListRepository = new PuzzleListRepository(context);
        AppDatabase.reset();
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void getRiddleTest() {
        List<PuzzleList> lists = mPuzzleListRepository.getAll();
        Espresso.onView(withId(R.id.skipbutton_admin)).perform(click());
        Espresso.onView(withId(R.id.buttonlist)).perform(click());
        for (PuzzleList puzzlelist : lists) {
            Espresso.onView(withText(puzzlelist.getName())).check(matches(isDisplayed()));
        }
        Espresso.onView(withText(lists.get(0).getName())).perform(click());
        PuzzleList l = lists.get(0);
        for (PuzzleList puzzlelist : lists) {
            mPuzzleListRepository.delete(puzzlelist);
        }
        mPuzzleListRepository.add(l);
        Espresso.onView(withId(R.id.button_close)).perform(click());
        Espresso.onView(withId(R.id.riddlelistrow_delete_button)).perform(click());
        assertEquals(0, mPuzzleListRepository.getAll().size());
        Espresso.onView(withId(R.id.floatingActionButton)).perform(click());
        Espresso.onView(withText("Add new Riddlelist")).check(matches(isDisplayed()));
    }
}