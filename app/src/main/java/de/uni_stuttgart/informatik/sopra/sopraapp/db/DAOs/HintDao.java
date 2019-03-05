package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Hint;

/**
 * Data Access Object for the Hint table.
 *
 * @author Stefan Scheffel , Mike Ashi
 */
@Dao
public interface HintDao {
    /**
     * Gets all hints from the table.
     *
     * @return List of all hints in the table
     */
    @Query("select * from hints")
    List<Hint> getAll();

    /**
     * Gets a hint from the table using its id.
     *
     * @param id hint's id
     * @return the hint with the given id
     */
    @Query("select * from hints where id=:id")
    Optional<Hint> getById(String id);

    /**
     * Get all hints for a given puzzle.
     *
     * @param puzzleId puzzle's id
     * @return List of all hints of a given puzzle in the table
     */
    @Query("select * from hints where puzzleId=:puzzleId")
    List<Hint> getPuzzleHints(String puzzleId);

    /**
     * Inserts one or more hint to the table.
     *
     * @param hint hint/s to be saved
     */
    @Insert
    void add(Hint... hint);

    /**
     * Updates a given hint.
     *
     * @param hint hint to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Hint... hint);

    /**
     * Deletes a given hint.
     *
     * @param hint hint to be deleted
     */
    @Delete
    void delete(Hint hint);
}

