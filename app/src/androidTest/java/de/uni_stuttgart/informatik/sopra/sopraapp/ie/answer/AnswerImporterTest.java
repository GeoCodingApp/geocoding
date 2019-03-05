package de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author MikeAshi
 */
public class AnswerImporterTest {
    private static final String TAG = "AnswerImporterTest";
    List<Answer> answers = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        json = "[{\"id\":\"answer_id1\",\"puzzle_id\":\"puzzle_id1\",\"elements\":\"W10=\"},{\"id\":\"answer_id2\",\"puzzle_id\":\"puzzle_id2\",\"elements\":\"W10=\"},{\"id\":\"answer_id3\",\"puzzle_id\":\"puzzle_id3\",\"elements\":\"W10=\"}]";
    }

    @Test
    public void toList() {
        answers = AnswerImporter.toList(json);
        Answer answer = answers.get(0);
        assertEquals(3, answers.size());
        assertEquals("answer_id1", answer.getId());
        assertEquals("puzzle_id1", answer.getPuzzleId());
    }

    @Test
    public void exceptionTest() {
        AnswerImporter answerImporter = new AnswerImporter();
        answers = answerImporter.toList("not a valid json");
        assertTrue(answers.isEmpty());
    }
}