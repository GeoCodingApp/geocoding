package de.uni_stuttgart.informatik.sopra.sopraapp.ie.event;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.event.EventSerializer;


/**
 * EventExporter uses the EventSerializer to serialize an event returning it as json.
 *
 * @author MikeAshi
 */
public class EventExporter {
    private static final String TAG = "EventExporter";
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns an event as json.
     *
     * @param event event to be exported
     * @return json representation of the given event
     */
    public static String toJson(Event event) {
        if (event == null) return "";
        SimpleModule module = new SimpleModule();
        module.addSerializer(Event.class, new EventSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return "";
    }
}
