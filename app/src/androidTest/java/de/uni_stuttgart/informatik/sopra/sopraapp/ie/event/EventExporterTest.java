package de.uni_stuttgart.informatik.sopra.sopraapp.ie.event;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

import static org.junit.Assert.assertEquals;

/**
 * @author MikeAshi
 */
public class EventExporterTest {
    private static final String TAG = "EventExporterTest";

    Event event;
    String json;

    @Before
    public void setUp() throws Exception {
        event = new Event("id", "name", Event.Status.STARTED);
        json = "{\"id\":\"id\",\"name\":\"name\",\"status\":\"0\"}";
    }

    @Test
    public void toJson() {
        EventExporter eventExporter = new EventExporter();
        String eventAsJson = eventExporter.toJson(event);
        assertEquals(json, eventAsJson);
    }
}