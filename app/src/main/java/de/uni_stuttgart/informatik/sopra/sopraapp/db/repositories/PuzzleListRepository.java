package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.PuzzleListDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

/**
 * Acts as a data holder to store all PuzzleLists.
 *
 * @author Stefan Scheffel, Mike Ashi
 */
public class PuzzleListRepository {

    private PuzzleListDao mPuzzleListDao;

    /**
     * Create new PuzzleListRepository instance.
     *
     * @param context application context
     */
    public PuzzleListRepository(Context context) {
        this.mPuzzleListDao = AppDatabase.getInstance(context).PuzzleListDao();
    }

    /**
     * Create new PuzzleListRepository instance.
     *
     * @param db AppDatabase
     */
    public PuzzleListRepository(AppDatabase db) {
        mPuzzleListDao = db.PuzzleListDao();
    }

    /**
     * Gets all puzzleLists from the database.
     *
     * @return List of all puzzleLists in the database
     */
    public List<PuzzleList> getAll() {
        return mPuzzleListDao.getAll();
    }

    /**
     * Gets a puzzleList from the database using its id.
     *
     * @param id puzzleList's id
     * @return the puzzleList with the given id
     */
    public Optional<PuzzleList> getById(String id) {
        return mPuzzleListDao.getById(id);
    }

    /**
     * Get all puzzleLists for a given event.
     *
     * @param eventId event's id
     * @return List of all puzzleLists of a given event in the database
     * @deprecated use the EventUserPuzzleListJoin
     */
    public List<PuzzleList> getAllForEvent(String eventId) {
        return mPuzzleListDao.getAllForEvent(eventId);
    }

    /**
     * Inserts a puzzleLists to the database.
     *
     * @param puzzleList puzzleList/s to be saved
     */
    public void add(PuzzleList puzzleList) {
        mPuzzleListDao.add(puzzleList);
    }

    /**
     * Inserts one or more puzzleLists to the database.
     *
     * @param puzzleList puzzleList/s to be saved
     */
    public void addAll(PuzzleList... puzzleList) {
        mPuzzleListDao.add(puzzleList);
    }

    /**
     * Updates a given puzzleList.
     *
     * @param puzzleList puzzleList to be updated
     */
    public void update(PuzzleList... puzzleList) {
        mPuzzleListDao.update(puzzleList);
    }

    /**
     * Deletes a given puzzleList.
     *
     * @param puzzleList puzzleList to be deleted
     */
    public void delete(PuzzleList... puzzleList) {
        mPuzzleListDao.delete(puzzleList);
    }
}
