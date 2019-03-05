package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditRiddleListActivityTest {

    @Rule
    public ActivityTestRule<EditRiddleListActivity> mActivityTestRule = new ActivityTestRule<>(EditRiddleListActivity.class, false, false);
    private AppDatabase db;
    private PuzzleRepository mPuzzleRepository;
    private PuzzleListRepository mPuzzleListRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AppDatabase.reset();
        mPuzzleRepository = new PuzzleRepository(context);
        mPuzzleListRepository = new PuzzleListRepository(context);
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void updateName() {
        PuzzleList puzzleList = getPuzzleList();
        // load activity
        Intent intent = new Intent();
        intent.putExtra("listid", puzzleList.getId());
        mActivityTestRule.launchActivity(intent);
        // change puzzleList name
        Espresso.onView(withId(R.id.editriddlelist_name)).perform(replaceText("new Name"));
        Espresso.onView(withId(R.id.button_edit_riddle_list_save)).perform(click());
        // check saved puzzleList
        Optional<PuzzleList> returnedList = mPuzzleListRepository.getById(puzzleList.getId());
        if (returnedList.isPresent()) {
            assertEquals("new Name", returnedList.get().getName());
        } else {
            fail();
        }
    }

    @Test
    public void addRiddlesToList() {
        List<Puzzle> puzzles = mPuzzleRepository.getAll();
        Puzzle puzzle = puzzles.get(0);
        for (Puzzle p : puzzles) {
            mPuzzleRepository.delete(p);
        }
        mPuzzleRepository.add(puzzle);
        //
        PuzzleList puzzleList = getPuzzleList();
        puzzleList.setPuzzlesIds(new ArrayList<String>());
        mPuzzleListRepository.update(puzzleList);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("listid", puzzleList.getId());
        mActivityTestRule.launchActivity(intent);
        // click add Riddles
        Espresso.onView(withId(R.id.add_riddles_riddlelist)).perform(click());
        // add Riddle
        Espresso.onView(withId(R.id.checkBox_add_riddle)).perform(click());
        // Save
        Espresso.onView(withId(R.id.fragment_edit_riddle_save)).perform(click());
        Espresso.onView(withId(R.id.button_edit_riddle_list_save)).perform(click());
        // check saved puzzleList
        Optional<PuzzleList> returnedList = mPuzzleListRepository.getById(puzzleList.getId());
        if (returnedList.isPresent()) {
            assertEquals(1, returnedList.get().getPuzzlesIds().size());
        } else {
            fail();
        }
    }

    @Test
    public void updatePuzzleTest() {
        List<Puzzle> puzzles = mPuzzleRepository.getAll();
        Puzzle puzzle = puzzles.get(0);
        for (Puzzle p : puzzles) {
            mPuzzleRepository.delete(p);
        }
        mPuzzleRepository.add(puzzle);
        //
        PuzzleList puzzleList = getPuzzleList();
        ArrayList<String> ids = new ArrayList<>();
        ids.add(puzzle.getId());
        puzzleList.setPuzzlesIds(ids);
        mPuzzleListRepository.update(puzzleList);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("listid", puzzleList.getId());
        mActivityTestRule.launchActivity(intent);
        // check riddle name
        Espresso.onView(withId(R.id.riddlename_riddlelist)).check(matches(withText(puzzle.getName())));
        // click on riddle
        Espresso.onView(withId(R.id.riddlename_riddlelist)).perform(click());
        // change name
        Espresso.onView(withId(R.id.puzzle_name)).perform(replaceText("xyz"));
        // save riddle
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // save list
        Espresso.onView(withId(R.id.button_edit_riddle_list_save)).perform(click());
        // check riddle name
        Optional<Puzzle> updatedPuzzle = mPuzzleRepository.getById(puzzle.getId());
        if (updatedPuzzle.isPresent()) {
            assertEquals("xyz", updatedPuzzle.get().getName());
        } else {
            fail();
        }
    }

    private PuzzleList getPuzzleList() {
        return mPuzzleListRepository.getAll().get(0);
    }
}