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
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class PuzzleRepositoryTest {
    private AppDatabase db;
    private PuzzleRepository puzzleRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        puzzleRepository = new PuzzleRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void add() {

        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Optional<Puzzle> returnedPuzzle = puzzleRepository.getByName(puzzle.getName());

        assertEquals("puzzleName", returnedPuzzle.get().getName());
    }

    @Test
    public void addAll() {

        Puzzle[] puzzles = {
                new Puzzle("1.Name", "1.Description", null),
                new Puzzle("2.Name", "2.Description", null),
                new Puzzle("3.Name", "3.Description", null),
                new Puzzle("4.Name", "4.Description", null),
                new Puzzle("5.Name", "5.Description", null),
                new Puzzle("6.Name", "6.Description", null),
                new Puzzle("7.Name", "7.Description", null),
                new Puzzle("8.Name", "8.Description", null),
                new Puzzle("9.Name", "9.Description", null),
                new Puzzle("10.Name", "10.Description", null),
        };

        puzzleRepository.addAll(puzzles);

        List<Puzzle> returnedPuzzles = puzzleRepository.getAll();

        assertEquals(10, returnedPuzzles.size());
    }

    @Test
    public void update() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Optional<Puzzle> returnedPuzzle = puzzleRepository.getByName(puzzle.getName());

        Puzzle dbPuzzle = returnedPuzzle.get();
        dbPuzzle.setName("updated name");
        dbPuzzle.setDescription("updated Description");
        puzzleRepository.update(dbPuzzle);

        returnedPuzzle = puzzleRepository.getById(dbPuzzle.getId());

        assertEquals("updated name", returnedPuzzle.get().getName());
        assertEquals("updated Description", returnedPuzzle.get().getDescription());
    }

    @Test
    public void delete() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Optional<Puzzle> returnedPuzzle = puzzleRepository.getByName(puzzle.getName());

        if (!returnedPuzzle.isPresent()) {
            fail("Puzzle not present");
        }

        puzzleRepository.delete(puzzle);

        returnedPuzzle = puzzleRepository.getById(puzzle.getId());
        if (returnedPuzzle.isPresent()) {
            fail("Puzzle was not deleted !!!");
        }

    }
}