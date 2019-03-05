package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.EventDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

/**
 * Acts as a data holder to store all events.
 *
 * @author Stefan Scheffel, MikeAshi
 */
public class EventRepository {

    private EventDao mEventDao;

    public EventRepository(Context context) {
        mEventDao = AppDatabase.getInstance(context).EventDao();
    }

    public EventRepository(AppDatabase db) {
        mEventDao = db.EventDao();
    }

    /**
     * Gets all Events from the database.
     *
     * @return List of all Events in the database
     */
    public List<Event> getAll() {
        return mEventDao.getAll();
    }

    /**
     * Gets all active Events from the table.
     *
     * @return List of all active Events in the table
     */
    public List<Event> getAllActiveEvents() {
        return mEventDao.getAllActiveEvents();
    }

    /**
     * Gets all stopped Events from the table.
     *
     * @return List of all stopped Events in the table
     */
    public List<Event> getAllStoppedEvents() {
        return mEventDao.getAllStoppedEvents();
    }

    /**
     * Gets all paused Events from the table.
     *
     * @return List of all paused Events in the table
     */
    public List<Event> getAllPausedEvents() {
        return mEventDao.getAllPausedEvents();
    }

    /**
     * Gets a Event from the database using its id.
     *
     * @param id Event's id
     * @return the Event with the given id
     */
    public Optional<Event> getById(String id) {
        return mEventDao.getById(id);
    }

    /**
     * Gets a Event from the database using his name.
     *
     * @param name Event's name
     * @return the Event with the given name
     */
    public Optional<Event> getByName(String name) {
        return mEventDao.getByName(name);
    }

    /**
     * Inserts an Event to the database.
     *
     * @param event Eventto be saved
     */
    public void add(Event event) {
        mEventDao.add(event);
    }

    /**
     * Inserts one or more Event to the database.
     *
     * @param event Event/s to be saved
     */
    public void addAll(Event... event) {
        mEventDao.add(event);
    }

    /**
     * Updates a given Event.
     *
     * @param event Event to be updated
     */
    public void update(Event... event) {
        mEventDao.update(event);
    }

    /**
     * Deletes a given Event.
     *
     * @param event Event to be deleted
     */
    public void delete(Event... event) {
        mEventDao.delete(event);
    }

}
