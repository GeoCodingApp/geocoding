package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzle.PuzzleDeserializer;

/**
 * PuzzleImporter uses the PuzzleDeserializer to deserialize a json string to Puzzle list.
 *
 * @author MikeAshi
 */
public class PuzzleImporter {

    private static final String TAG = "PuzzleImporter";


    /**
     * Returns a List of puzzles from json
     *
     * @param json json
     * @return a List of Puzzles
     */
    public static List<Puzzle> toList(String json) {
        List<Puzzle> puzzles = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Puzzle.class, new PuzzleDeserializer());
        mapper.registerModule(module);
        try {
            puzzles = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Puzzle.class));
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return puzzles;
    }
}
