package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class EventUserPuzzleListJoinRepositoryTest {
    private AppDatabase db;
    private PuzzleListRepository puzzleListRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private EventUserPuzzleListJoinRepository eventUserPuzzleListJoinRepository;
    private PuzzleRepository puzzleRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        puzzleListRepository = new PuzzleListRepository(db);
        userRepository = new UserRepository(db);
        eventRepository = new EventRepository(db);
        eventUserPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(db);
        puzzleRepository = new PuzzleRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void add() {
        // create puzzleLists
        PuzzleList puzzleList1 = new PuzzleList("puzzleList1");
        puzzleListRepository.add(puzzleList1);
        PuzzleList puzzleList2 = new PuzzleList("puzzleList2");
        puzzleListRepository.add(puzzleList2);
        // create users
        User user1 = new User("user1", "pass", false);
        userRepository.add(user1);
        User user2 = new User("user2", "pass", false);
        userRepository.add(user2);
        // create Event
        Event event = new Event("Event name");
        eventRepository.add(event);
        // add puzzleList1 to event and assign it to user1
        EventUserPuzzleListJoin join = new EventUserPuzzleListJoin(event.getId(), puzzleList1.getId());
        join.setUserId(user1.getId());
        eventUserPuzzleListJoinRepository.add(join);
        // add puzzleList2 to event and assign it to user2
        join = new EventUserPuzzleListJoin(event.getId(), puzzleList2.getId(), user2.getId());
        eventUserPuzzleListJoinRepository.add(join);

        List<PuzzleList> returnedPuzzleLists = eventUserPuzzleListJoinRepository.getAllPuzzleListForEvent(event.getId());
        assertEquals(2, returnedPuzzleLists.size());

        Optional<PuzzleList> userPuzzleList = eventUserPuzzleListJoinRepository.getPuzzleListForEventUser(event.getId(), user1.getId());
        assertEquals("puzzleList1", userPuzzleList.get().getName());

        userPuzzleList = eventUserPuzzleListJoinRepository.getPuzzleListForEventUser(event.getId(), user2.getId());
        assertEquals("puzzleList2", userPuzzleList.get().getName());

    }

    @Test
    public void addAll() {
        // create user
        User user = new User("user1", "pass", false);
        userRepository.add(user);
        // create Event
        Event event = new Event("Event name");
        eventRepository.add(event);
        // create puzzleList
        PuzzleList puzzleList = new PuzzleList("puzzleList1");
        puzzleListRepository.add(puzzleList);
        //
        EventUserPuzzleListJoin[] joins = new EventUserPuzzleListJoin[]{
                new EventUserPuzzleListJoin(event.getId(), puzzleList.getId(), user.getId()),
                new EventUserPuzzleListJoin(event.getId(), puzzleList.getId(), user.getId()),
                new EventUserPuzzleListJoin(event.getId(), puzzleList.getId(), user.getId()),
                new EventUserPuzzleListJoin(event.getId(), puzzleList.getId(), user.getId()),
        };
        eventUserPuzzleListJoinRepository.addAll(joins);
        assertEquals(4, eventUserPuzzleListJoinRepository.getAll().size());
    }

    @Test
    public void delete() {
        // create user
        User user = new User("user1", "pass", false);
        userRepository.add(user);
        // create Event
        Event event = new Event("Event name");
        eventRepository.add(event);
        // create puzzleList
        PuzzleList puzzleList = new PuzzleList("puzzleList1");
        puzzleListRepository.add(puzzleList);
        //
        EventUserPuzzleListJoin join = new EventUserPuzzleListJoin(event.getId(), puzzleList.getId(), user.getId());
        eventUserPuzzleListJoinRepository.add(join);
        assertTrue(eventUserPuzzleListJoinRepository.getById(join.getId()).isPresent());
        assertEquals(1, eventUserPuzzleListJoinRepository.getByUserId(user.getId()).size());
        eventUserPuzzleListJoinRepository.delete(join);
        assertFalse(eventUserPuzzleListJoinRepository.getById(join.getId()).isPresent());
    }

    @Test
    public void update() {
        // create puzzleList
        PuzzleList puzzleList = new PuzzleList("puzzleList1");
        puzzleListRepository.add(puzzleList);
        // create users
        User user = new User("user1", "pass", false);
        userRepository.add(user);
        // create Event
        Event event = new Event("Event name");
        eventRepository.add(event);
        // add puzzleList to the event
        EventUserPuzzleListJoin join = new EventUserPuzzleListJoin(event.getId(), puzzleList.getId());
        eventUserPuzzleListJoinRepository.add(join);

        List<PuzzleList> returnedPuzzleLists = eventUserPuzzleListJoinRepository.getAllPuzzleListForEvent(event.getId());
        assertEquals(1, returnedPuzzleLists.size());
        Optional<PuzzleList> userPuzzleList = eventUserPuzzleListJoinRepository.getPuzzleListForEventUser(event.getId(), user.getId());

        if (userPuzzleList.isPresent()) {
            fail("Error");
        }
        Optional<EventUserPuzzleListJoin> returnedJoin = eventUserPuzzleListJoinRepository.getById(join.getId());
        EventUserPuzzleListJoin dbJoin = returnedJoin.get();
        dbJoin.setUserId(user.getId());
        eventUserPuzzleListJoinRepository.update(dbJoin);

        userPuzzleList = eventUserPuzzleListJoinRepository.getPuzzleListForEventUser(event.getId(), user.getId());

        assertEquals("puzzleList1", userPuzzleList.get().getName());
    }

    @Test
    public void solvedPuzzlesTest() {
        // create users
        User user = new User("user1", "pass", false);
        userRepository.add(user);
        // create Event
        Event event = new Event("Event name");
        eventRepository.add(event);
        // create puzzles
        Puzzle[] puzzles = {
                new Puzzle("1.Name", "1.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("2.Name", "2.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("3.Name", "3.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("4.Name", "4.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("5.Name", "5.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("6.Name", "6.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("7.Name", "7.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("8.Name", "8.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("9.Name", "9.Description", new ArrayList<PuzzleViewElement>()),
                new Puzzle("10.Name", "10.Description", new ArrayList<PuzzleViewElement>()),
        };

        puzzleRepository.addAll(puzzles);
        // create PuzzleList
        PuzzleList puzzleList = new PuzzleList("List name");
        puzzleListRepository.add(puzzleList);
        // add some puzzles to the List
        ArrayList<String> ids = new ArrayList<>();
        ids.add(puzzles[0].getId());
        ids.add(puzzles[1].getId());
        ids.add(puzzles[2].getId());
        ids.add(puzzles[3].getId());
        ids.add(puzzles[4].getId());
        puzzleList.setPuzzlesIds(ids);
        puzzleListRepository.update(puzzleList);
        // add puzzleList to the event
        EventUserPuzzleListJoin join = new EventUserPuzzleListJoin(event.getId(), puzzleList.getId());
        join.setUserId(user.getId());
        eventUserPuzzleListJoinRepository.add(join);
        // get puzzleList for the user
        Optional<PuzzleList> dbPuzzleList = eventUserPuzzleListJoinRepository.getPuzzleListForEventUser(event.getId(), user.getId());
        // get puzzles for the user
        List<Puzzle> returnedPuzzles = puzzleRepository.getById(puzzleList.getPuzzlesIds());
        assertEquals(5, returnedPuzzles.size());
        ///////////////////////////////////////////////////////////
        // solve first 3 puzzles
        eventUserPuzzleListJoinRepository.addSolvedPuzzle(join, returnedPuzzles.get(0).getId());
        eventUserPuzzleListJoinRepository.addSolvedPuzzle(join, returnedPuzzles.get(1).getId());
        eventUserPuzzleListJoinRepository.addSolvedPuzzle(join, returnedPuzzles.get(2).getId());

        assertEquals(3, eventUserPuzzleListJoinRepository.getSolvedPuzzles(join).size());
    }
}