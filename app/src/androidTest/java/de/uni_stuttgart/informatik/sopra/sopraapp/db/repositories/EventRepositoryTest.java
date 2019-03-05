package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class EventRepositoryTest {
    private AppDatabase db;
    private EventRepository eventRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        eventRepository = new EventRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void add() {
        Event event = new Event("test");
        eventRepository.add(event);
        Optional<Event> returnedEvent = eventRepository.getById(event.getId());
        assertEquals("test", returnedEvent.get().getName());
    }

    @Test
    public void addAll() {
        Event[] events = {
                new Event("1.test"),
                new Event("2.test"),
                new Event("3.test"),
                new Event("4.test"),
                new Event("5.test"),
        };
        eventRepository.addAll(events);

        List<Event> returnedEvents = eventRepository.getAll();
        assertEquals(5, returnedEvents.size());
    }

    @Test
    public void update() {
        Event event = new Event("test");
        eventRepository.add(event);

        Optional<Event> returnedEvent = eventRepository.getById(event.getId());

        Event dbEvent = returnedEvent.get();
        dbEvent.setName("updated text");

        eventRepository.update(dbEvent);
        returnedEvent = eventRepository.getById(event.getId());

        assertEquals("updated text", returnedEvent.get().getName());
    }

    @Test
    public void delete() {
        Event event = new Event("test");
        eventRepository.add(event);

        Optional<Event> returnedEvent = eventRepository.getById(event.getId());

        if (!returnedEvent.isPresent()) {
            fail("Event not present");
        }

        eventRepository.delete(event);
        returnedEvent = eventRepository.getById(event.getId());

        if (returnedEvent.isPresent()) {
            fail("Event was not deleted !!!");
        }
    }

    @Test
    public void getByStatus() {
        Event[] events = {
                new Event("started event id1", "started event1", Event.Status.STARTED),
                new Event("started event id2", "started event2", Event.Status.STARTED),
                new Event("started event id3", "started event3", Event.Status.STARTED),
                new Event("started event id4", "started event4", Event.Status.STARTED),
                new Event("paused event id1", "paused event1", Event.Status.PAUSED),
                new Event("paused event id2", "paused event2", Event.Status.PAUSED),
                new Event("paused event id3", "paused event3", Event.Status.PAUSED),
                new Event("stopped event id1", "stopped event1", Event.Status.STOPPED),
                new Event("stopped event id2", "stopped event2", Event.Status.STOPPED),
                new Event("stopped event id3", "stopped event3", Event.Status.STOPPED),
                new Event("stopped event id4", "stopped event4", Event.Status.STOPPED),
                new Event("stopped event id5", "stopped event5", Event.Status.STOPPED),
        };
        eventRepository.addAll(events);
        List<Event> returnedStartedEvents = eventRepository.getAllActiveEvents();
        List<Event> returnedPausedEvents = eventRepository.getAllPausedEvents();
        List<Event> returnedStoppedEvents = eventRepository.getAllStoppedEvents();
        assertEquals(4, returnedStartedEvents.size());
        assertEquals(3, returnedPausedEvents.size());
        assertEquals(5, returnedStoppedEvents.size());
    }

    @Test
    public void getByName() {
        Event event = new Event("test name");
        eventRepository.add(event);
        Optional<Event> returnedEvent = eventRepository.getByName(event.getName());
        assertEquals(event.getId(), returnedEvent.get().getId());
    }

}