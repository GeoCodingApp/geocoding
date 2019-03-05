package de.uni_stuttgart.informatik.sopra.sopraapp.db.entities;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Stefan
 */
public class PuzzleListTests {

    public static final String PUZZLE_LIST_NAME = "PUZZLE_LIST_NAME";
    public static final String PUZZLE_LIST_EVENT_ID = "EVENT_ID";
    private PuzzleList puzzleList;

    @Before
    public void initTest() {
        puzzleList = new PuzzleList(PUZZLE_LIST_NAME, PUZZLE_LIST_EVENT_ID);
    }

    @Test
    public void testConstructor() {
        assertEquals(PUZZLE_LIST_NAME, puzzleList.getName());
        assertEquals(PUZZLE_LIST_EVENT_ID, puzzleList.getEventId());
    }

    @Test
    public void testSetName() {
        String testName = "this is just a test name";
        assertNotEquals(testName, puzzleList.getName());
        puzzleList.setName(testName);
        assertEquals(testName, puzzleList.getName());
    }

    @Test
    public void testSetEventId() {
        String testId = "this is just a test id";
        assertNotEquals(testId, puzzleList.getEventId());
        puzzleList.setEventId(testId);
        assertEquals(testId, puzzleList.getEventId());
    }

    @Test
    public void testSetId() {
        String testId = "this is a test id";
        assertNotEquals(testId, puzzleList.getId());
        puzzleList.setId(testId);
        assertEquals(testId, puzzleList.getId());
    }
}
