package de.uni_stuttgart.informatik.sopra.sopraapp.db.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AnswerTests {

    private static final String ANSWER_ID = "ANSWER_ID";
    private static final String ANSWER_PUZZLE_ID = "ANSWER_PUZZLE_ID";
    private static final String ANSWER_TEXT = "ANSWER_TEXT";
    private static final String ANSWER_LATITUDE = "48.745452";
    private static final String ANSWER_LONGITUDE = "9.102146";
    private static final ArrayList<AnswerViewElement> ANSWER_ElEMENTS = new ArrayList<>(
            Arrays.asList(new AnswerViewElement(AnswerViewElement.Type.TEXT, ANSWER_TEXT),
                    new AnswerViewElement(AnswerViewElement.Type.LOCATION, ANSWER_LATITUDE, ANSWER_LONGITUDE)));
    private Answer testAnswer;

    @Before
    public void initAnswers() {
        this.testAnswer = new Answer(ANSWER_ID, ANSWER_ElEMENTS, ANSWER_PUZZLE_ID);
    }

    @Test
    public void testConstructor() {
        assertEquals(ANSWER_ID, testAnswer.getId());
        assertEquals(ANSWER_ElEMENTS, testAnswer.getElements());
        assertEquals(ANSWER_PUZZLE_ID, testAnswer.getPuzzleId());
    }

    @Test
    public void testSetAnswerElements() {
        ArrayList<AnswerViewElement> newList = new ArrayList<>();
        this.testAnswer.setElements(newList);
        assertNotEquals(ANSWER_ElEMENTS, this.testAnswer.getElements());
    }

    @Test
    public void testAnswerViewElementText() {
        AnswerViewElement textElement = this.testAnswer.getElements().get(0);
        assertEquals(AnswerViewElement.Type.TEXT, textElement.getType());
        assertEquals(ANSWER_TEXT, textElement.getText());
    }

    @Test
    public void testAnswerViewElementLocation() {
        AnswerViewElement locationElement = this.testAnswer.getElements().get(1);
        assertEquals(AnswerViewElement.Type.LOCATION, locationElement.getType());
        assertEquals(ANSWER_LATITUDE, locationElement.getLatitude());
        assertEquals(ANSWER_LONGITUDE, locationElement.getLongitude());
    }

    /**
     * Tests if the puzzle id is set properly
     */
    @Test
    public void testAnswerSetPuzzleId() {
        String testPuzzleId = "this is some random puzzle id for testing purposes";
        assertNotEquals("Test puzzle IDs must be different", testPuzzleId, ANSWER_PUZZLE_ID);
        testAnswer.setPuzzleId(testPuzzleId);
        assertEquals(testPuzzleId, this.testAnswer.getPuzzleId());
    }
}
