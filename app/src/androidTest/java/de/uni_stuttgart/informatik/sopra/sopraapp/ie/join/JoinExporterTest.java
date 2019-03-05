package de.uni_stuttgart.informatik.sopra.sopraapp.ie.join;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author MikeAshi
 */
public class JoinExporterTest {
    private static final String TAG = "JoinExporterTest";

    List<EventUserPuzzleListJoin> join = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        join.add(new EventUserPuzzleListJoin("id", "event_id", "puzzle_list_id", "user_id", "solved_puzzles"));
        json = "[{\"id\":\"id\",\"event_id\":\"event_id\",\"puzzle_list_id\":\"puzzle_list_id\",\"user_id\":\"user_id\",\"solved_puzzles\":\"c29sdmVkX3B1enpsZXM=\"}]";
    }

    @Test
    public void toJson() {
        String joinAsJson = JoinExporter.toJson(join);
        assertEquals(json, joinAsJson);
    }

    @Test
    public void exceptionTest() throws JsonProcessingException {
        ObjectMapper mapper = spy(new ObjectMapper());
        when(mapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Exception thrown") {
        });
        JoinExporter joinExporter = new JoinExporter();
        joinExporter.mapper = mapper;
        json = joinExporter.toJson(new ArrayList<EventUserPuzzleListJoin>());
        assertEquals("", json);
    }
}