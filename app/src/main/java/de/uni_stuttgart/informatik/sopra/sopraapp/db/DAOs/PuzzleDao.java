package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

/**
 * Data Access Object for the Puzzle table.
 *
 * @author Stefan Scheffel, MikeAshi
 */
@Dao
public interface PuzzleDao {
    /**
     * Gets all puzzles from the table.
     *
     * @return List of all puzzles in the table
     */
    @Query("select * from puzzles")
    List<Puzzle> getAll();

    /**
     * Gets a puzzle from the table using its id.
     *
     * @param id puzzle's id
     * @return the puzzle with the given id
     */
    @Query("select * from puzzles where id=:id")
    Optional<Puzzle> getById(String id);

    /**
     * Gets a puzzle from the table using its name.
     *
     * @param name puzzle's name
     * @return the puzzle with the given name
     */
    @Query("select * from puzzles where name=:name")
    Optional<Puzzle> getByName(String name);

    /**
     * Inserts one or more puzzle to the table.
     *
     * @param puzzle puzzle/s to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Puzzle... puzzle);

    /**
     * Updates a given puzzle.
     *
     * @param puzzle puzzle to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Puzzle... puzzle);

    /**
     * Deletes a given puzzle.
     *
     * @param puzzle puzzle to be deleted
     */
    @Delete
    void delete(Puzzle puzzle);

}
