package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author MikeAshi
 */
public class PuzzleListExporterTest {
    private static final String TAG = "PuzzleListExporterTest";
    List<PuzzleList> lists = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        initLists();
        json = "[{\"id\":\"list_id1\",\"name\":\"list_name1\",\"event_id\":\"event_id1\",\"puzzles_ids\":\"W10=\"},{\"id\":\"list_id2\",\"name\":\"list_name2\",\"event_id\":\"event_id2\",\"puzzles_ids\":\"W10=\"},{\"id\":\"list_id3\",\"name\":\"list_name3\",\"event_id\":\"event_id3\",\"puzzles_ids\":\"W10=\"}]";
    }

    private void initLists() {
        lists.add(new PuzzleList("list_id1", "list_name1", "event_id1", new ArrayList<String>()));
        lists.add(new PuzzleList("list_id2", "list_name2", "event_id2", new ArrayList<String>()));
        lists.add(new PuzzleList("list_id3", "list_name3", "event_id3", new ArrayList<String>()));
    }

    @Test
    public void toJson() {
        String listsAsJson = PuzzleListExporter.toJson(lists);
        Log.d(TAG, "toJson: " + listsAsJson);
        assertEquals(json, listsAsJson);
    }

    @Test
    public void exceptionTest() throws JsonProcessingException {
        PuzzleListExporter listExporter = new PuzzleListExporter();
        ObjectMapper mapper = spy(new ObjectMapper());
        when(mapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Exception thrown") {
        });
        listExporter.mapper = mapper;
        json = listExporter.toJson(new ArrayList<PuzzleList>());
        assertEquals("", json);
    }
}