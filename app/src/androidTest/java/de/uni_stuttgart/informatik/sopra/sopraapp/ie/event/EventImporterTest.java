package de.uni_stuttgart.informatik.sopra.sopraapp.ie.event;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author MikeAshi
 */
public class EventImporterTest {

    String json;
    Event event;

    @Before
    public void setUp() throws Exception {
        json = "{\"id\":\"id\",\"name\":\"name\",\"status\":\"0\"}";
    }

    @Test
    public void toObject() {
        EventImporter eventImporter = new EventImporter();
        event = eventImporter.toObject(json);
        assertEquals("id", event.getId());
        assertEquals("name", event.getName());
        assertEquals(Event.Status.STARTED, event.getStatus());
    }

    @Test
    public void exceptionTest() {
        event = EventImporter.toObject("not a valid json");
        assertNull(event);
    }
}