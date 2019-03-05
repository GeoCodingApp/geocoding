package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author MikeAshi
 */
public class PuzzleListImporterTest {
    private static final String TAG = "PuzzleListImporterTest";
    List<PuzzleList> lists = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        json = "[{\"id\":\"list_id1\",\"name\":\"list_name1\",\"event_id\":\"event_id1\",\"puzzles_ids\":\"[]\"},{\"id\":\"list_id2\",\"name\":\"list_name2\",\"event_id\":\"event_id2\",\"puzzles_ids\":\"[]\"},{\"id\":\"list_id3\",\"name\":\"list_name3\",\"event_id\":\"event_id3\",\"puzzles_ids\":\"[]\"}]";
    }

    @Test
    public void toList() {
        lists = PuzzleListImporter.toList(json);
        PuzzleList list = lists.get(0);
        assertEquals(3, lists.size());
        assertEquals("list_id1", list.getId());
        assertEquals("list_name1", list.getName());
    }

    @Test
    public void exceptionTest() {
        PuzzleListImporter listImporter = new PuzzleListImporter();
        lists = listImporter.toList("not a valid json");
        assertTrue(lists.isEmpty());
    }
}