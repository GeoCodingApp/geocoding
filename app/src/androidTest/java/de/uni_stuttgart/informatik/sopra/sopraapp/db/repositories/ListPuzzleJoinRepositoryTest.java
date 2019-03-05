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

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ListPuzzleJoinRepositoryTest {
    private AppDatabase db;
    private PuzzleRepository puzzleRepository;
    private PuzzleListRepository puzzleListRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        puzzleRepository = new PuzzleRepository(db);
        puzzleListRepository = new PuzzleListRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void add() {
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
        // get puzzles from puzzleList
        List<Puzzle> returnedPuzzles = puzzleRepository.getById(puzzleList.getPuzzlesIds());
        assertEquals(5, returnedPuzzles.size());
    }
}