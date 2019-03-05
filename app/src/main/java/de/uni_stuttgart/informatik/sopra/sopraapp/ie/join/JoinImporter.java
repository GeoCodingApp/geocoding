package de.uni_stuttgart.informatik.sopra.sopraapp.ie.join;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.join.JoinDeserializer;

/**
 * JoinImporter uses the JoinDeserializer to deserialize a json string to EventUserPuzzleListJoin.
 *
 * @author MikeAshi
 */
public class JoinImporter {
    private static final String TAG = "JoinImporter";

    /**
     * Returns a List of EventUserPuzzleListJoin from json
     *
     * @param json json
     * @return a List of EventUserPuzzleListJoin
     */
    public static List<EventUserPuzzleListJoin> toList(String json) {
        List<EventUserPuzzleListJoin> joins = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(EventUserPuzzleListJoin.class, new JoinDeserializer());
        mapper.registerModule(module);

        try {
            joins = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, EventUserPuzzleListJoin.class));
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return joins;
    }
}
