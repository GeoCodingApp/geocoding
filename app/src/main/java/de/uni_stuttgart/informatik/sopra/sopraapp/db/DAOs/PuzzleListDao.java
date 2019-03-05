package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

/**
 * Data Access Object for the PuzzleList table.
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@Dao
public interface PuzzleListDao {
    /**
     * Gets all puzzleLists from the table.
     *
     * @return List of all puzzleLists in the table
     */
    @Query("select * from puzzlelists")
    List<PuzzleList> getAll();

    /**
     * Gets a puzzleList from the table using its id.
     *
     * @param id puzzleList's id
     * @return the puzzleList with the given id
     */
    @Query("select * from puzzlelists where id=:id")
    Optional<PuzzleList> getById(String id);

    /**
     * Get all puzzleLists for a given event.
     *
     * @param eventId event's id
     * @return List of all puzzleLists of a given event in the table
     */
    @Query("select * from puzzlelists where eventId=:eventId")
    List<PuzzleList> getAllForEvent(String eventId);

    /**
     * Inserts one or more puzzleLists to the table.
     *
     * @param puzzleList puzzleList/s to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(PuzzleList... puzzleList);

    /**
     * Updates a given puzzleList.
     *
     * @param puzzleList puzzleList to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(PuzzleList... puzzleList);

    /**
     * Deletes a given puzzleList.
     *
     * @param puzzleList puzzleList to be deleted
     */
    @Delete
    void delete(PuzzleList... puzzleList);

}
