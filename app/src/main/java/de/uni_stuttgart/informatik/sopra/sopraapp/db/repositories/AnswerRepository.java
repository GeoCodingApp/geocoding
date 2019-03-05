package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.AnswerDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;

/**
 * Acts as a data holder to store all answers.
 *
 * @author Stefan Scheffel, MikeAshi
 */
public class AnswerRepository {

    private AnswerDao mAnswerDao;

    /**
     * Create new AnswerRepository instance.
     *
     * @param context application context
     */
    public AnswerRepository(Context context) {

        mAnswerDao = AppDatabase.getInstance(context).AnswerDao();
    }

    /**
     * Create new AnswerRepository instance.
     *
     * @param db AppDatabase
     */
    public AnswerRepository(AppDatabase db) {

        mAnswerDao = db.AnswerDao();
    }

    /**
     * Gets all answers from the database.
     *
     * @return List of all answers in the database
     */
    public List<Answer> getAll() {
        return mAnswerDao.getAll();
    }

    /**
     * Gets an answer from the database using its id.
     *
     * @param id answer's id
     * @return the answer with the given id
     */
    public Optional<Answer> getById(String id) {
        return mAnswerDao.getById(id);
    }

    /**
     * Gets all answers from the database.
     *
     * @return List of all answers in the database
     */
    public List<Answer> getSolutions() {
        return mAnswerDao.getSolutions();
    }

    /**
     * Get all answers for a given puzzle.
     *
     * @param puzzleId puzzle's id
     * @return List of all answers of a given puzzle in the database
     */
    public Optional<Answer> getPuzzleSolution(String puzzleId) {
        return mAnswerDao.getPuzzleSolution(puzzleId);
    }

    /**
     * Inserts an answer to the database.
     *
     * @param answer answer to be saved
     */
    public void add(Answer answer) {
        mAnswerDao.add(answer);
    }

    /**
     * Inserts one or more answer to the database.
     *
     * @param answer answer/s to be saved
     */
    public void addAll(Answer... answer) {
        mAnswerDao.add(answer);
    }

    /**
     * Updates a given answer.
     *
     * @param answer answer to be updated
     */
    public void update(Answer... answer) {
        mAnswerDao.update(answer);
    }

    /**
     * Deletes a given answer.
     *
     * @param answer answer to be deleted
     */
    public void delete(Answer... answer) {
        mAnswerDao.delete(answer);
    }
}
