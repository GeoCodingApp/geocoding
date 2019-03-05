package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.HintDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Hint;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

/**
 * Acts as a data holder to store all hints.
 *
 * @author Stefan Scheffel, MikeAshi
 */
@SuppressWarnings("unused")
public class HintsRepository {

    private HintDao mHintDao;

    /**
     * Create new HintsRepository instance.
     *
     * @param context application context
     */
    public HintsRepository(Context context) {
        mHintDao = AppDatabase.getInstance(context).HintDao();
    }

    /**
     * Create new HintsRepository instance.
     *
     * @param db AppDatabase
     */
    public HintsRepository(AppDatabase db) {
        mHintDao = db.HintDao();
    }

    /**
     * Gets all hints from the database.
     *
     * @return List of all hints in the database
     */
    public List<Hint> getAll() {
        return mHintDao.getAll();
    }

    /**
     * Gets a hint from the database using its id.
     *
     * @param id hint's id
     * @return the hint with the given id
     */
    public Optional<Hint> getById(String id) {
        return mHintDao.getById(id);
    }

    /**
     * Get all hints for a given puzzle id.
     *
     * @param puzzleId puzzle's id
     * @return List of all hints of a given puzzle in the database
     */
    public List<Hint> getPuzzleHints(String puzzleId) {
        return mHintDao.getPuzzleHints(puzzleId);
    }

    /**
     * Returns all hints for a given Puzzle
     *
     * @param puzzle The puzzle to get the hints from
     * @return The hints for this puzzle
     */
    public List<Hint> getPuzzleHints(Puzzle puzzle) {
        return mHintDao.getPuzzleHints(puzzle.getId());
    }

    /**
     * Inserts a hint to the database.
     *
     * @param hint hint to be saved
     */
    public void add(Hint hint) {
        mHintDao.add(hint);
    }

    /**
     * Inserts one or more hint to the database.
     *
     * @param hint hint/s to be saved
     */
    public void addAll(Hint... hint) {
        mHintDao.add(hint);
    }

    /**
     * Updates a given hint.
     *
     * @param hint hint to be updated
     */
    public void update(Hint... hint) {
        mHintDao.update(hint);
    }

    /**
     * Deletes a given hint.
     *
     * @param hint hint to be deleted
     */
    public void delete(Hint hint) {
        mHintDao.delete(hint);
    }
}
