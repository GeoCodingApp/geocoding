package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;

/**
 * Data Access Object for the Answer table.
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@Dao
public interface AnswerDao {
    /**
     * Gets all answers from the table.
     *
     * @return List of all answers in the table
     */
    @Query("select * from answers")
    List<Answer> getAll();

    /**
     * Gets an answer from the table using its id.
     *
     * @param id answer's id
     * @return the answer with the given id
     */
    @Query("select * from answers where id=:id")
    Optional<Answer> getById(String id);

    /**
     * Gets all answers from the table.
     *
     * @return List of all answers in the table
     */
    @Query("select * from answers")
    List<Answer> getSolutions();

    /**
     * Get all answers for a given puzzle.
     *
     * @param puzzleId puzzle's id
     * @return List of all answers of a given puzzle in the table
     */
    @Query("select * from answers where puzzleId=:puzzleId")
    Optional<Answer> getPuzzleSolution(String puzzleId);

    /**
     * Returns all available answers for the given Puzzle
     *
     * @param puzzleId the id of the puzzle
     * @return the Answers for this puzzle
     */
    @Query("select * from answers where puzzleId=:puzzleId")
    List<Answer> getAnswersForPuzzle(String puzzleId);

    /**
     * Inserts one or more answer to the table.
     *
     * @param answer answer/s to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Answer... answer);

    /**
     * Updates a given answer.
     *
     * @param answer answer to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Answer... answer);

    /**
     * Deletes a given answer.
     *
     * @param answer answer to be deleted
     */
    @Delete
    void delete(Answer... answer);

}
