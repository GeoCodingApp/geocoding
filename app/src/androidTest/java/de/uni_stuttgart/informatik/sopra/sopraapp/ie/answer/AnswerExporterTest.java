package de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author MikeAshi
 */
public class AnswerExporterTest {
    private static final String TAG = "AnswerExporterTest";
    List<Answer> answers = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        initAnswerList();
        json = "[{\"id\":\"answer_id1\",\"puzzle_id\":\"puzzle_id1\",\"elements\":\"W10=\"},{\"id\":\"answer_id2\",\"puzzle_id\":\"puzzle_id2\",\"elements\":\"W10=\"},{\"id\":\"answer_id3\",\"puzzle_id\":\"puzzle_id3\",\"elements\":\"W10=\"}]";
    }

    private void initAnswerList() {
        answers.add(new Answer("answer_id1", new ArrayList<AnswerViewElement>(), "puzzle_id1"));
        answers.add(new Answer("answer_id2", new ArrayList<AnswerViewElement>(), "puzzle_id2"));
        answers.add(new Answer("answer_id3", new ArrayList<AnswerViewElement>(), "puzzle_id3"));
    }

    @Test
    public void toJson() {
        String answerAsJson = AnswerExporter.toJson(answers);
        assertEquals(json, answerAsJson);
    }

    @Test
    public void exceptionTest() throws JsonProcessingException {
        AnswerExporter answerAsJson = new AnswerExporter();
        ObjectMapper mapper = spy(new ObjectMapper());
        when(mapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Exception thrown") {
        });
        answerAsJson.mapper = mapper;
        json = answerAsJson.toJson(new ArrayList<Answer>());
        assertEquals("", json);
    }
}