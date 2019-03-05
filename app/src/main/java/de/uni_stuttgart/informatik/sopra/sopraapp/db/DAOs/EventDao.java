package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

/**
 * Data Access Object for the Event table.
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@Dao
public interface EventDao {
    /**
     * Gets all Events from the table.
     *
     * @return List of all Events in the table
     */
    @Query("select * from events")
    List<Event> getAll();

    /**
     * Gets all active Events from the table.
     *
     * @return List of all active Events in the table
     */
    @Query("select * from events where status=0")
    List<Event> getAllActiveEvents();

    /**
     * Gets all stopped Events from the table.
     *
     * @return List of all stopped Events in the table
     */
    @Query("select * from events where status=-1")
    List<Event> getAllStoppedEvents();

    /**
     * Gets all paused Events from the table.
     *
     * @return List of all paused Events in the table
     */
    @Query("select * from events where status=1")
    List<Event> getAllPausedEvents();

    /**
     * Gets a Event from the table using its id.
     *
     * @param id Event's id
     * @return the Event with the given id
     */
    @Query("select * from events where id=:id")
    Optional<Event> getById(String id);

    /**
     * Gets a Event from the table using his name.
     *
     * @param name Event's name
     * @return the Event with the given name
     */
    @Query("select * from events where name=:name")
    Optional<Event> getByName(String name);

    /**
     * Inserts one or more Event to the table.
     *
     * @param event Event/s to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Event... event);

    /**
     * Updates a given Event.
     *
     * @param event Event to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Event... event);

    /**
     * Deletes a given Event.
     *
     * @param event Event to be deleted
     */
    @Delete
    void delete(Event... event);
}
