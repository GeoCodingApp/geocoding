package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author MikeAshi
 */
public class PuzzleExporterTest {

    private static final String TAG = "PuzzleExporterTest";
    List<Puzzle> puzzles = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        initPuzzleList();
        json = "[{\"id\":\"puzzle_id1\",\"name\":\"puzzle_name1\",\"description\":\"puzzle_description1\",\"elements\":\"W10=\"},{\"id\":\"puzzle_id2\",\"name\":\"puzzle_name2\",\"description\":\"puzzle_description2\",\"elements\":\"W10=\"},{\"id\":\"puzzle_id3\",\"name\":\"puzzle_name3\",\"description\":\"puzzle_description3\",\"elements\":\"W10=\"}]";
    }

    private void initPuzzleList() {
        puzzles.add(new Puzzle("puzzle_id1", "puzzle_name1", "puzzle_description1", new ArrayList<PuzzleViewElement>()));
        puzzles.add(new Puzzle("puzzle_id2", "puzzle_name2", "puzzle_description2", new ArrayList<PuzzleViewElement>()));
        puzzles.add(new Puzzle("puzzle_id3", "puzzle_name3", "puzzle_description3", new ArrayList<PuzzleViewElement>()));
    }

    @Test
    public void toJson() {
        String puzzlesAsJson = PuzzleExporter.toJson(puzzles);
        assertEquals(json, puzzlesAsJson);
    }

    @Test
    public void exceptionTest() throws JsonProcessingException {
        PuzzleExporter puzzleExporter = new PuzzleExporter();
        ObjectMapper mapper = spy(new ObjectMapper());
        when(mapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Exception thrown") {
        });
        puzzleExporter.mapper = mapper;
        json = puzzleExporter.toJson(new ArrayList<Puzzle>());
        assertEquals("", json);
    }
}