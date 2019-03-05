package de.uni_stuttgart.informatik.sopra.sopraapp.db.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Stefan
 */
public class PuzzleTests {

    public static final String PUZZLE_NAME = "PUZZLE_NAME";
    public static final String PUZZLE_DESCRIPTION = "PUZZLE_DESCRIPTION";
    public static final String PUZZLE_TEXT = "PUZZLE_QUESTION";
    public static final String PUZZLE_CODE = "PUZZLE_CODE";
    public static final ArrayList<PuzzleViewElement> PUZZLE_ElEMENTS = new ArrayList<>(
            Arrays.asList(new PuzzleViewElement(PuzzleViewElement.Type.CODE, PUZZLE_CODE),
                    new PuzzleViewElement(PuzzleViewElement.Type.TEXT, PUZZLE_TEXT)));

    private Puzzle testPuzzle;

    @Before
    public void initTest() {
        testPuzzle = new Puzzle(PUZZLE_NAME, PUZZLE_DESCRIPTION, PUZZLE_ElEMENTS);
    }

    @Test
    public void testConstructor() {
        assertEquals(PUZZLE_NAME, testPuzzle.getName());
        assertEquals(PUZZLE_DESCRIPTION, testPuzzle.getDescription());
        assertEquals(PUZZLE_ElEMENTS, testPuzzle.getElements());
    }

    @Test
    public void testSetId() {
        String testId = "this is just a test id";
        assertNotEquals(testId, testPuzzle.getId());
        testPuzzle.setId(testId);
        assertEquals(testId, testPuzzle.getId());
    }

    @Test
    public void testSetName() {
        String testName = "this is just a test name";
        assertNotEquals(testName, testPuzzle.getName());
        testPuzzle.setName(testName);
        assertEquals(testName, testPuzzle.getName());
    }

    @Test
    public void testSetDescription() {
        String testDescr = "this is just a test description";
        assertNotEquals(testDescr, testPuzzle.getDescription());
        testPuzzle.setDescription(testDescr);
        assertEquals(testDescr, testPuzzle.getDescription());
    }

    @Test
    public void testSetElements() {
        ArrayList<PuzzleViewElement> list = new ArrayList<>();
        assertNotEquals(list, testPuzzle.getElements());
        testPuzzle.setElements(list);
        assertEquals(list, testPuzzle.getElements());
    }

    @Test
    public void testEquals() {
        Puzzle firstPuzzle = new Puzzle("1", null, null, null);
        Puzzle secondPuzzle = new Puzzle("2", null, null, null);
        assertNotEquals(firstPuzzle, secondPuzzle);
    }
}
