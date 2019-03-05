package de.uni_stuttgart.informatik.sopra.sopraapp.ie.join;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author MikeAshi
 */
public class JoinImporterTest {

    String json;
    List<EventUserPuzzleListJoin> joins;

    @Before
    public void setUp() throws Exception {
        json = "[{\"id\":\"id\",\"event_id\":\"event_id\",\"puzzle_list_id\":\"puzzle_list_id\",\"user_id\":\"user_id\",\"solved_puzzles\":\"c29sdmVkX3B1enpsZXM=\"}]";
    }

    @Test
    public void toList() {
        joins = JoinImporter.toList(json);
        EventUserPuzzleListJoin join = joins.get(0);
        assertEquals("id", join.getId());
        assertEquals("event_id", join.getEventId());
        assertEquals("puzzle_list_id", join.getPuzzleListId());
        assertEquals("user_id", join.getUserId());
        assertEquals("solved_puzzles", join.getSolvedPuzzles());
    }

    @Test
    public void exceptionTest() {
        JoinImporter joinImporter = new JoinImporter();
        joins = joinImporter.toList("not a valid json");
        assertTrue(joins.isEmpty());
    }
}