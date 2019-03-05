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
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class PuzzleListRepositoryTest {
    private AppDatabase db;
    private PuzzleListRepository puzzleListRepository;
    private EventRepository eventRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        puzzleListRepository = new PuzzleListRepository(db);
        eventRepository = new EventRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void add() {
        PuzzleList puzzleList = new PuzzleList("test");
        puzzleListRepository.add(puzzleList);

        Optional<PuzzleList> returnedPuzzleList = puzzleListRepository.getById(puzzleList.getId());
        assertEquals("test", returnedPuzzleList.get().getName());
    }

    @Test
    public void addAll() {
        PuzzleList[] puzzleLists = {
                new PuzzleList("1.test"),
                new PuzzleList("2.test"),
                new PuzzleList("3.test"),
                new PuzzleList("4.test"),
                new PuzzleList("5.test"),
                new PuzzleList("6.test"),
                new PuzzleList("7.test"),
                new PuzzleList("8.test"),
                new PuzzleList("9.test"),
                new PuzzleList("10.test"),
        };

        puzzleListRepository.addAll(puzzleLists);

        List<PuzzleList> returnedPuzzleLists = puzzleListRepository.getAll();
        assertEquals(10, returnedPuzzleLists.size());
    }

    @Test
    public void update() {
        PuzzleList puzzleList = new PuzzleList("test");
        puzzleListRepository.add(puzzleList);

        Optional<PuzzleList> returnedPuzzleList = puzzleListRepository.getById(puzzleList.getId());

        PuzzleList dbPuzzleList = returnedPuzzleList.get();
        dbPuzzleList.setName("updated test");

        puzzleListRepository.update(dbPuzzleList);
        returnedPuzzleList = puzzleListRepository.getById(dbPuzzleList.getId());

        assertEquals("updated test", returnedPuzzleList.get().getName());
    }

    @Test
    public void delete() {
        PuzzleList puzzleList = new PuzzleList("test");
        puzzleListRepository.add(puzzleList);

        Optional<PuzzleList> returnedPuzzleList = puzzleListRepository.getById(puzzleList.getId());

        if (!returnedPuzzleList.isPresent()) {
            fail("PuzzleList not present");
        }

        puzzleListRepository.delete(puzzleList);
        returnedPuzzleList = puzzleListRepository.getById(puzzleList.getId());

        if (returnedPuzzleList.isPresent()) {
            fail("PuzzleList was not deleted !!!");
        }
    }

    @Test
    public void getAllForEvent() {
        Event event = new Event("test Event");
        eventRepository.add(event);

        PuzzleList[] puzzleLists = {
                new PuzzleList("1.test", event.getId()),
                new PuzzleList("2.test", event.getId()),
                new PuzzleList("3.test", event.getId()),
                new PuzzleList("4.test", event.getId()),
                new PuzzleList("5.test", event.getId()),
                new PuzzleList("6.test"),
                new PuzzleList("7.test"),
                new PuzzleList("8.test"),
                new PuzzleList("9.test"),
                new PuzzleList("10.test"),
        };

        puzzleListRepository.addAll(puzzleLists);

        List<PuzzleList> returnedPuzzleLists = puzzleListRepository.getAllForEvent(event.getId());

        assertEquals(5, returnedPuzzleLists.size());
    }
}