package de.uni_stuttgart.informatik.sopra.sopraapp.ie.join;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.join.JoinSerializer;

/**
 * JoinExporter uses the JoinSerializer to serialize an EventUserPuzzleListJoin returning it as json.
 *
 * @author MikeAshi
 */
public class JoinExporter {
    private static final String TAG = "JoinExporter";
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of EventUserPuzzleListJoin as json.
     *
     * @param joins join to be exported
     * @return json representation of the given event
     */
    public static String toJson(List<EventUserPuzzleListJoin> joins) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(EventUserPuzzleListJoin.class, new JoinSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(joins);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return "";
    }
}
