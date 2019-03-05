package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author MikeAshi
 */
public class PuzzleImporterTest {
    private static final String TAG = "PuzzleImporterTest";

    List<Puzzle> puzzles = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        json = "[{\"id\":\"puzzle_id1\",\"name\":\"puzzle_name1\",\"description\":\"puzzle_description1\",\"elements\":\"W10=\"},{\"id\":\"puzzle_id2\",\"name\":\"puzzle_name2\",\"description\":\"puzzle_description2\",\"elements\":\"W10=\"},{\"id\":\"puzzle_id3\",\"name\":\"puzzle_name3\",\"description\":\"puzzle_description3\",\"elements\":\"W10=\"}]";
    }

    @Test
    public void toList() {
        puzzles = PuzzleImporter.toList(json);
        Puzzle puzzle = puzzles.get(0);
        assertEquals(3, puzzles.size());
        assertEquals("puzzle_id1", puzzle.getId());
        assertEquals("puzzle_name1", puzzle.getName());
    }

    @Test
    public void exceptionTest() {
        PuzzleImporter puzzleImporter = new PuzzleImporter();
        puzzles = puzzleImporter.toList("not a valid json");
        assertTrue(puzzles.isEmpty());
    }

}