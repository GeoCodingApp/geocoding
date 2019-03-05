package de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleList.PuzzleListDeserializer;

/**
 * PuzzleListImporter uses the PuzzleListDeserializer to deserialize a json string to PuzzleList list.
 *
 * @author MikeAshi
 */
public class PuzzleListImporter {
    private static final String TAG = "PuzzleImporter";


    /**
     * Returns a List of puzzleLists from json
     *
     * @param json json
     * @return a List of PuzzleLists
     */
    public static List<PuzzleList> toList(String json) {
        List<PuzzleList> lists = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(PuzzleList.class, new PuzzleListDeserializer());
        mapper.registerModule(module);
        try {
            lists = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, PuzzleList.class));
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return lists;
    }
}
