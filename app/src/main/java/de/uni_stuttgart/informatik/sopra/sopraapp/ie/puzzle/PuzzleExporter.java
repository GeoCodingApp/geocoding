package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzle.PuzzleSerializer;

/**
 * PuzzleExporter uses the PuzzleSerializer to serialize a List of Puzzles returning it as json.
 *
 * @author MikeAshi
 */
public class PuzzleExporter {
    private static final String TAG = "PuzzleExporter";
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of puzzles as json.
     *
     * @param puzzles join to be exported
     * @return json representation of the given list
     */
    public static String toJson(List<Puzzle> puzzles) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Puzzle.class, new PuzzleSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(puzzles);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return "";
    }

}
