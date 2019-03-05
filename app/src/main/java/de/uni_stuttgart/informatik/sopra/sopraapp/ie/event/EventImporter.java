package de.uni_stuttgart.informatik.sopra.sopraapp.ie.event;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.event.EventDeserializer;

/**
 * EventImporter uses the EventDeserializer to deserialize a json string to event.
 *
 * @author MikeAshi
 */
public class EventImporter {
    private static final String TAG = "EventImporter";

    /**
     * Returns event from json
     *
     * @param json json
     * @return event
     */
    public static Event toObject(String json) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Event.class, new EventDeserializer());
        mapper.registerModule(module);
        Event event = null;
        try {
            event = mapper.readValue(json, Event.class);
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return event;
    }
}
