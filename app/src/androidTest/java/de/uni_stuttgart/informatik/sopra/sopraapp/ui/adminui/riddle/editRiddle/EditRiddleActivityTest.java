package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Base64;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.AnswerRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditRiddleActivityTest {
    @Rule
    public ActivityTestRule<EditRiddleActivity> mActivityTestRule = new ActivityTestRule<>(EditRiddleActivity.class, false, false);
    private AppDatabase db;
    private PuzzleRepository mPuzzleRepository;
    private AnswerRepository mAnswerRepository;
    private ImagesRepository mImagesRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mPuzzleRepository = new PuzzleRepository(context);
        AppDatabase.reset();
        mAnswerRepository = new AnswerRepository(context);
        mImagesRepository = new ImagesRepository(context);
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void EditRiddleNameTest() {
        Puzzle puzzle = getPuzzle();
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // check puzzle name
        Espresso.onView(withId(R.id.puzzle_name)).check(matches(withText(puzzle.getName())));
    }

    @Test
    public void EditRiddleChangeName() {
        Puzzle puzzle = getPuzzle();
        mPuzzleRepository.update(puzzle);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // change puzzle name
        Espresso.onView(withId(R.id.puzzle_name)).perform(replaceText("new Name"));
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // check saved puzzle
        Optional<Puzzle> returnedPuzzle = mPuzzleRepository.getById(puzzle.getId());
        if (returnedPuzzle.isPresent()) {
            assertEquals("new Name", returnedPuzzle.get().getName());
        } else {
            fail();
        }

    }

    @Test
    public void EditRiddleAddText() {
        Puzzle puzzle = getPuzzle();
        puzzle.setElements(new ArrayList<PuzzleViewElement>());
        mPuzzleRepository.update(puzzle);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // click add text
        Espresso.onView(withId(R.id.btn_add_text)).perform(click());
        // Enter text
        Espresso.onView(withId(R.id.puzzle_text)).perform(typeText("testing text element"));
        // click save button
        Espresso.onView(withId(R.id.puzzle_text_button_save)).perform(click());
        // click save button
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // check text element
        Optional<Puzzle> returnedPuzzle = mPuzzleRepository.getById(puzzle.getId());
        if (returnedPuzzle.isPresent()) {
            assertEquals("testing text element", returnedPuzzle.get().getElements().get(0).getText());
        } else {
            fail();
        }
    }

    @Test
    public void EditRiddleAddCode() {
        Puzzle puzzle = getPuzzle();
        puzzle.setElements(new ArrayList<PuzzleViewElement>());
        mPuzzleRepository.update(puzzle);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // click add text
        Espresso.onView(withId(R.id.btn_add_code)).perform(click());
        // Enter code
        Espresso.onView(withId(R.id.puzzle_code)).perform(typeText("test code"));
        // click save button
        Espresso.onView(withId(R.id.code_button_save)).perform(click());
        // click save button
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // check code element
        Optional<Puzzle> returnedPuzzle = mPuzzleRepository.getById(puzzle.getId());
        if (returnedPuzzle.isPresent()) {
            assertEquals("test code", returnedPuzzle.get().getElements().get(0).getCode());
        } else {
            fail();
        }
    }

    @Test
    public void EditRiddleUpdateText() {
        Puzzle puzzle = getPuzzle();
        ArrayList<PuzzleViewElement> elements = new ArrayList<>();
        elements.add(new PuzzleViewElement(PuzzleViewElement.Type.TEXT, "Text test"));
        puzzle.setElements(elements);
        mPuzzleRepository.update(puzzle);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // click on text element
        Espresso.onView(withId(R.id.card_view)).perform(click());
        // check text
        Espresso.onView(withId(R.id.puzzle_text)).check(matches(withText(puzzle.getElements().get(0).getText())));
        // replace text
        Espresso.onView(withId(R.id.puzzle_text)).perform(replaceText("new text"));
        // click save
        Espresso.onView(withId(R.id.puzzle_text_button_save)).perform(click());
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // check text element
        Optional<Puzzle> returnedPuzzle = mPuzzleRepository.getById(puzzle.getId());
        if (returnedPuzzle.isPresent()) {
            assertEquals("new text", returnedPuzzle.get().getElements().get(0).getText());
        } else {
            fail();
        }
    }

    @Test
    public void EditRiddleUpdateCode() {
        Puzzle puzzle = getPuzzle();
        ArrayList<PuzzleViewElement> elements = new ArrayList<>();
        elements.add(new PuzzleViewElement(PuzzleViewElement.Type.CODE, "code test"));
        puzzle.setElements(elements);
        mPuzzleRepository.update(puzzle);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // click on code element
        Espresso.onView(withText("Code Segment")).perform(click());
        // check code
        Espresso.onView(withId(R.id.puzzle_code)).check(matches(withText(puzzle.getElements().get(0).getCode())));
        // replace code
        Espresso.onView(withId(R.id.puzzle_code)).perform(replaceText("new code"));
        // click save
        Espresso.onView(withId(R.id.code_button_save)).perform(click());
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // check code element
        Optional<Puzzle> returnedPuzzle = mPuzzleRepository.getById(puzzle.getId());
        if (returnedPuzzle.isPresent()) {
            assertEquals("new code", returnedPuzzle.get().getElements().get(0).getCode());
        } else {
            fail();
        }
    }

    @Test
    public void EditRiddleAddAnswerText() {
        Puzzle puzzle = getPuzzle();
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // click on Add Answer
        Espresso.onView(withId(R.id.puzzle_answer_btn)).perform(click());
        // click on add text
        Espresso.onView(withId(R.id.btn_addAnsewer_text)).perform(click());
        // add answer text
        Espresso.onView(withId(R.id.answer_text)).perform(typeText("added answer text"));
        // click on save button
        Espresso.onView(withId(R.id.button_answer_text_save)).perform(click());
        // update answer text
        Espresso.onView(withText("added answer text")).perform(click());
        Espresso.onView(withId(R.id.button_answer_text_save)).perform(click());
        // add Location
        Espresso.onView(withId(R.id.btn_add_Location)).perform(click());
        Espresso.onView(withId(R.id.answer_latitude)).perform(typeText("1"));
        Espresso.onView(withId(R.id.answer_longitude)).perform(typeText("2"));
        Espresso.onView(withId(R.id.button_save)).perform(click());
        Espresso.onView(withId(R.id.answer_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Espresso.onView(withId(R.id.button_save)).perform(click());
        // save answer
        Espresso.onView(withId(R.id.button_answer_save)).perform(click());
        // check answer
        Answer answer = mAnswerRepository.getPuzzleSolution(puzzle.getId()).get();
        assertEquals("added answer text", answer.getElements().get(1).getText());
    }

    @Test
    public void EditRiddleShowImageElement() {
        // add image to db
        Image image = new Image(Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==", Base64.DEFAULT));
        mImagesRepository.add(image);
        // setup the puzzle
        Puzzle puzzle = getPuzzle();
        ArrayList<PuzzleViewElement> elements = new ArrayList<>();
        elements.add(new PuzzleViewElement(PuzzleViewElement.Type.IMG, image.getId()));
        puzzle.setElements(elements);
        mPuzzleRepository.update(puzzle);
        // load activity
        Intent intent = new Intent();
        intent.putExtra("id", puzzle.getId());
        mActivityTestRule.launchActivity(intent);
        // check if image displayed
        Espresso.onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.imageView)).perform(click());
    }

    @Test
    public void saveNewRiddle() {
        // load activity
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        // add riddle name
        Espresso.onView(withId(R.id.puzzle_name)).perform(replaceText("new Name"));
        // click add text
        Espresso.onView(withId(R.id.btn_add_text)).perform(click());
        // Enter text
        Espresso.onView(withId(R.id.puzzle_text)).perform(typeText("testing text element"));
        // click save button
        Espresso.onView(withId(R.id.puzzle_text_button_save)).perform(click());
        // click on Add Answer
        Espresso.onView(withId(R.id.puzzle_answer_btn)).perform(click());
        // click on add text
        Espresso.onView(withId(R.id.btn_addAnsewer_text)).perform(click());
        // add answer text
        Espresso.onView(withId(R.id.answer_text)).perform(typeText("added answer text"));
        // click on save button
        Espresso.onView(withId(R.id.button_answer_text_save)).perform(click());
        // save answer
        Espresso.onView(withId(R.id.button_answer_save)).perform(click());
        // save riddle
        // Espresso.onView(withId(R.id.button_save)).perform(click());
    }

    private Puzzle getPuzzle() {
        List<Puzzle> puzzles = mPuzzleRepository.getAll();
        return puzzles.get(1);
    }
}