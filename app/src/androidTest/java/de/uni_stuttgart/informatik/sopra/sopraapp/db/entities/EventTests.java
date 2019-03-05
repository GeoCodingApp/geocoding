package de.uni_stuttgart.informatik.sopra.sopraapp.db.entities;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Stefan
 */
public class EventTests {

    public static final String EVENT_NAME = "EVENT_NAME";
    private Event testEvent;

    @Before
    public void initTest() {
        this.testEvent = new Event(EVENT_NAME);
    }

    @Test
    public void testConstructor() {
        assertEquals(testEvent.getName(), EVENT_NAME);
    }

    @Test
    public void testEventSetName() {
        String testName = "this is a new event name";
        assertNotEquals("Test name must be different", testName, testEvent.getName());
        testEvent.setName(testName);
        assertEquals(testName, testEvent.getName());
    }

    @Test
    public void testEventId() {
        String testId = "this is a event test id";
        assertNotEquals("Test id must be different", testId, testEvent.getId());
        testEvent.setId(testId);
        assertEquals(testId, testEvent.getId());
    }

    @Test
    public void testEventStatus() {
        assertEquals(Event.Status.WARM_UP, testEvent.getStatus());
        testEvent.setStatus(Event.Status.STARTED);
        assertEquals(Event.Status.STARTED, testEvent.getStatus());
    }
}
