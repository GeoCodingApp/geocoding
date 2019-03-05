package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleList.PuzzleListSerializer;

/**
 * PuzzleListExporter uses the PuzzleListSerializer to serialize a List of PuzzleList returning it as json.
 *
 * @author MikeAshi
 */
public class PuzzleListExporter {
    private static final String TAG = "PuzzleListExporter";
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of puzzles as json.
     *
     * @param puzzleList join to be exported
     * @return json representation of the given list
     */
    public static String toJson(List<PuzzleList> puzzleList) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(PuzzleList.class, new PuzzleListSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(puzzleList);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return "";
    }
}
