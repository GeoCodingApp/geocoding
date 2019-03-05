package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle;

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
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.LoginActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class RiddleTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    PuzzleRepository mPuzzleRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mPuzzleRepository = new PuzzleRepository(context);
        AppDatabase.reset();
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void getRiddleTest() {
        List<Puzzle> puzzles = mPuzzleRepository.getAll();
        Espresso.onView(withId(R.id.skipbutton_admin)).perform(click());
        Espresso.onView(withId(R.id.buttonriddle)).perform(click());
        for (Puzzle puzzle : puzzles) {
            Espresso.onView(withText(puzzle.getName())).check(matches(isDisplayed()));
        }
        Espresso.onView(withText(puzzles.get(0).getName())).perform(click());
        Puzzle p = puzzles.get(0);
        for (Puzzle puzzle : puzzles) {
            mPuzzleRepository.delete(puzzle);
        }
        mPuzzleRepository.add(p);
        Espresso.onView(withId(R.id.button_close)).perform(click());
        Espresso.onView(withId(R.id.riddlerow_delete_button)).perform(click());
        assertEquals(0, mPuzzleRepository.getAll().size());
        Espresso.onView(withId(R.id.floatingActionButton)).perform(click());
        Espresso.onView(withText("New riddle")).check(matches(isDisplayed()));
    }
}