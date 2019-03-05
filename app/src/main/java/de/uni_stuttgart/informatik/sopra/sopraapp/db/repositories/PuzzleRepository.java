package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.PuzzleDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

/**
 * Acts as a data holder to store all puzzles.
 *
 * @author Stefan Scheffel,Mike Ashi
 */
public class PuzzleRepository {

    private PuzzleDao mPuzzleDao;

    /**
     * Create new PuzzleRepository instance.
     *
     * @param context application context
     */
    public PuzzleRepository(Context context) {
        mPuzzleDao = AppDatabase.getInstance(context).PuzzleDao();
    }

    /**
     * Create new PuzzleRepository instance.
     *
     * @param db AppDatabase
     */
    public PuzzleRepository(AppDatabase db) {
        mPuzzleDao = db.PuzzleDao();
    }

    /**
     * Gets all puzzles from the database.
     *
     * @return List of all puzzles in the database
     */
    public List<Puzzle> getAll() {
        return mPuzzleDao.getAll();
    }

    /**
     * Gets a puzzle from the database using its id.
     *
     * @param id puzzle's id
     * @return the puzzle with the given id
     */
    public Optional<Puzzle> getById(String id) {
        return mPuzzleDao.getById(id);
    }

    /**
     * Gets a list of all puzzle from the database using a list of ids.
     *
     * @param ids puzzle's id
     * @return list of all puzzles with matching id
     */
    public List<Puzzle> getById(ArrayList<String> ids) {
        ArrayList<Puzzle> puzzles = new ArrayList<>();
        if (ids != null) {
            for (String id : ids) {
                Optional<Puzzle> puzzle = getById(id);
                if (puzzle.isPresent()) {
                    puzzles.add(puzzle.get());
                }
            }
        }
        return puzzles;
    }


    /**
     * Gets a puzzle from the database using its name.
     *
     * @param name puzzle's name
     * @return the puzzle with the given name
     */
    public Optional<Puzzle> getByName(String name) {
        return mPuzzleDao.getByName(name);
    }

    /**
     * Inserts a puzzle to the database.
     *
     * @param puzzle puzzle to be saved
     */
    public void add(Puzzle puzzle) {
        mPuzzleDao.add(puzzle);
    }

    /**
     * Inserts one or more puzzle to the database.
     *
     * @param puzzle puzzle/s to be saved
     */
    public void addAll(Puzzle... puzzle) {
        mPuzzleDao.add(puzzle);
    }

    /**
     * Updates a given puzzle.
     *
     * @param puzzle puzzle to be updated
     */
    public void update(Puzzle... puzzle) {
        mPuzzleDao.update(puzzle);
    }

    /**
     * Deletes a given puzzle.
     *
     * @param puzzle puzzle to be deleted
     */
    public void delete(Puzzle puzzle) {
        mPuzzleDao.delete(puzzle);
    }
}
