package de.uni_stuttgart.informatik.sopra.sopraapp.db.converters;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleElement.PuzzleElementDeserializer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleElement.PuzzleElementSerializer;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * PuzzleConverter used by room to store/restore ArrayList<PuzzleViewElement> in the database.
 *
 * @author MikeAshi
 */
public class PuzzleConverter {
    private static final String TAG = "PuzzleConverter";

    @TypeConverter
    public static String toString(List<PuzzleViewElement> elements) {
        Log.d(TAG, "toString: Converting Puzzle Elements to json");
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(PuzzleViewElement.class, new PuzzleElementSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(elements);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @TypeConverter
    public static List<PuzzleViewElement> toList(String json) {
        Log.d(TAG, "toString: Converting json to Puzzle Elements");

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(PuzzleViewElement.class, new PuzzleElementDeserializer());
        mapper.registerModule(module);
        try {
            List<PuzzleViewElement> arrayList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, PuzzleViewElement.class));
            return arrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
