package de.uni_stuttgart.informatik.sopra.sopraapp.db.entities;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Hint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Stefan
 */
public class HintTests {

    private static final String TEST_HINT_TEXT = "HINT_TEXT";
    private static final String TEST_HINT_PUZZLE_ID = "PUZZLE_ID";
    private Hint testHint;

    @Before
    public void initTest() {
        testHint = new Hint(TEST_HINT_TEXT, TEST_HINT_PUZZLE_ID);
    }

    @Test
    public void testConstructor() {
        // No assignments - just test.
        assertEquals(TEST_HINT_TEXT, testHint.getText());
        assertEquals(TEST_HINT_PUZZLE_ID, testHint.getPuzzleId());
    }

    @Test
    public void testSetText() {
        String testText = "just a test text";
        assertNotEquals("Test text must be different", testText, testHint.getText());
        testHint.setText(testText);
        assertEquals(testText, testHint.getText());
    }

    @Test
    public void testSetId() {
        String testId = "this is a test Id";
        assertNotEquals(testId, testHint.getId());
        testHint.setId(testId);
        assertEquals(testId, testHint.getId());
    }

    @Test
    public void testSetPuzzleId() {
        String testPuzzleId = "just a random puzzle id";
        assertNotEquals(testPuzzleId, testHint.getPuzzleId());
        testHint.setPuzzleId(testPuzzleId);
        assertEquals(testPuzzleId, testHint.getPuzzleId());
    }
}
