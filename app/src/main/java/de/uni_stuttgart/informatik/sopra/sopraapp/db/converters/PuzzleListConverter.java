package de.uni_stuttgart.informatik.sopra.sopraapp.db.converters;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * PuzzleList converter used by room to store/restore ArrayList<String> in the database.
 *
 * @author MikeAshi
 */
public class PuzzleListConverter {
    private static final String TAG = "PuzzleListConverter";

    @TypeConverter
    public static String toString(ArrayList<String> ids) {
        Log.d(TAG, "toString: Converting Puzzle ids to json");
        if (ids == null) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(ids);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @TypeConverter
    public static ArrayList<String> toList(String json) {
        Log.d(TAG, "toString: Converting json to ArrayList<String>");
        if (json.equals("")) {
            return new ArrayList<>();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String[] puzzleIds = mapper.readValue(json, String[].class);
            ArrayList<String> newList = new ArrayList<>();
            Collections.addAll(newList, puzzleIds);
            return newList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
