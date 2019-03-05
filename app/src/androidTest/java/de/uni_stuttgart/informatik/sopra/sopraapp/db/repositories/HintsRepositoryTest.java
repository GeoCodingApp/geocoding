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
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Hint;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class HintsRepositoryTest {
    private AppDatabase db;
    private HintsRepository hintsRepository;
    private PuzzleRepository puzzleRepository;


    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        hintsRepository = new HintsRepository(context);
        hintsRepository = new HintsRepository(db);
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

        Hint hint = new Hint("text", puzzle.getId());
        hintsRepository.add(hint);

        Optional<Hint> returnedHint = hintsRepository.getById(hint.getId());

        assertEquals("text", returnedHint.get().getText());
    }

    @Test
    public void addAll() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Hint[] hints = {
                new Hint("1.text", puzzle.getId()),
                new Hint("2.text", puzzle.getId()),
                new Hint("3.text", puzzle.getId()),
                new Hint("4.text", puzzle.getId()),
                new Hint("5.text", puzzle.getId()),
                new Hint("6.text", puzzle.getId()),
                new Hint("7.text", puzzle.getId()),
                new Hint("8.text", puzzle.getId()),
                new Hint("9.text", puzzle.getId()),
                new Hint("10.text", puzzle.getId()),
        };
        hintsRepository.addAll(hints);

        List<Hint> returnedHints = hintsRepository.getAll();

        assertEquals(10, returnedHints.size());
    }


    @Test
    public void update() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Hint hint = new Hint("text", puzzle.getId());
        hintsRepository.add(hint);

        Optional<Hint> returnedHint = hintsRepository.getById(hint.getId());
        Hint dbHint = returnedHint.get();

        dbHint.setText("updated text");
        hintsRepository.update(dbHint);

        returnedHint = hintsRepository.getById(dbHint.getId());
        assertEquals("updated text", returnedHint.get().getText());


    }

    @Test
    public void delete() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Hint hint = new Hint("text", puzzle.getId());
        hintsRepository.add(hint);
        Optional<Hint> returnedHint = hintsRepository.getById(hint.getId());

        if (!returnedHint.isPresent()) {
            fail("Hint not present");
        }

        hintsRepository.delete(hint);

        returnedHint = hintsRepository.getById(hint.getId());
        if (returnedHint.isPresent()) {
            fail("Hint was not deleted !!!");
        }


    }

    @Test
    public void getPuzzleHints() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Hint[] hints = {
                new Hint("1.text", puzzle.getId()),
                new Hint("2.text", puzzle.getId()),
                new Hint("3.text", puzzle.getId()),
        };
        hintsRepository.addAll(hints);

        List<Hint> returnedHints = hintsRepository.getPuzzleHints(puzzle.getId());
        assertEquals(3, returnedHints.size());

    }
}